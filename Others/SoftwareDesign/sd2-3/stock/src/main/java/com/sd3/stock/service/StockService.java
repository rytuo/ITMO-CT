package com.sd3.stock.service;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sd3.stock.model.CompaniyStock;
import com.sd3.stock.model.Company;
import com.sd3.stock.model.Stock;
import com.sd3.stock.repository.CompaniesRepository;
import com.sd3.stock.repository.CompaniesStocksRepository;
import com.sd3.stock.repository.StocksRepository;

@Service
public class StockService {

    private final CompaniesRepository companiesRepository;
    private final StocksRepository stocksRepository;
    private final CompaniesStocksRepository companiesStocksRepository;

    @Autowired
    public StockService(
            CompaniesRepository companiesRepository,
            StocksRepository stocksRepository,
            CompaniesStocksRepository companiesStocksRepository
    ) {
        this.companiesRepository = companiesRepository;
        this.stocksRepository = stocksRepository;
        this.companiesStocksRepository = companiesStocksRepository;
    }

    public long createCompany() {
        var company = new Company();
        company = companiesRepository.save(company);
        return company.id;
    }

    public long createStock(long companyId) {
        var stock = new Stock();
        stock = stocksRepository.save(stock);

        var companyStock = new CompaniyStock();
        companyStock.companyId = companyId;
        companyStock.stockId = stock.id;
        companiesStocksRepository.save(companyStock);

        return stock.id;
    }

    public void addStocks(long stockId, long amount) {
        if (amount < 1) {
            throw new RuntimeException("Amount must be positive");
        }
        withStock(stockId, stock -> stock.amount += amount);
    }

    public Stock getStock(long stockId) {
        return stocksRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("No stock found"));
    }

    public List<Stock> getStocks() {
        return stocksRepository.findAll();
    }

    public void buyStocks(long stockId, long amount, long price) {
        if (amount < 1) {
            throw new RuntimeException("Amount must be positive");
        }
        withStock(stockId, stock -> {
            if (price != stock.price) {
                throw new RuntimeException("Stock price is not valid");
            }
            if (stock.amount < amount) {
                throw new RuntimeException("Not enough stocks");
            }
            stock.amount -= amount;
        });
    }

    public void sellStocks(long stockId, long amount, long price) {
        if (amount < 1) {
            throw new RuntimeException("Amount must be positive");
        }
        withStock(stockId, stock -> {
            if (price != stock.price) {
                throw new RuntimeException("Stock price is not valid");
            }
            stock.amount += amount;
        });
    }

    public void changeStockPrice(long stockId, long newPrice) {
        withStock(stockId, stock -> stock.price = newPrice);
    }


    @Transactional
    void withStock(long stockId, Consumer<Stock> callback) {
        var stock = getStock(stockId);
        callback.accept(stock);
        stocksRepository.save(stock);
    }
}
