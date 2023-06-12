package com.sd3.stock.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sd3.stock.model.CompaniyStock;
import com.sd3.stock.model.Company;
import com.sd3.stock.model.Stock;
import com.sd3.stock.repository.CompaniesRepository;
import com.sd3.stock.repository.CompaniesStocksRepository;
import com.sd3.stock.repository.StocksRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private CompaniesRepository companiesRepository;

    @Mock
    private StocksRepository stocksRepository;

    @Mock
    private CompaniesStocksRepository companiesStocksRepository;

    private StockService stockService;

    @BeforeEach
    void beforeEach() {
        stockService = new StockService(
                companiesRepository,
                stocksRepository,
                companiesStocksRepository
        );
    }

    @Test
    public void createCompanyTest() {
        long companyId = 4128799;
        var company = new Company();
        company.id = companyId;
        Mockito.when(companiesRepository.save(ArgumentMatchers.any()))
                .thenReturn(company);
        assertThat(stockService.createCompany()).isEqualTo(companyId);
    }

    @Test
    public void createStockTest() {
        long companyId = 7318;
        var stock = createStock(1414, 9518, 310);

        Mockito.when(stocksRepository.save(ArgumentMatchers.any()))
                        .thenReturn(stock);

        stockService.createStock(companyId);

        ArgumentCaptor<CompaniyStock> captor = ArgumentCaptor.forClass(CompaniyStock.class);
        Mockito.verify(companiesStocksRepository).save(captor.capture());
        var companyStock = captor.getValue();
        assertThat(companyStock.stockId).isEqualTo(stock.id);
        assertThat(companyStock.companyId).isEqualTo(companyId);
    }

    @Test
    public void addStocksTest() {
        long amount0 = 138 , amount = 156, id = 175, price = 88540;
        var stock = createStock(id, amount0, price);

        Mockito.when(stocksRepository.findById(id))
                .thenReturn(Optional.of(stock));

        stockService.addStocks(id, amount);

        ArgumentCaptor<Stock> captor = ArgumentCaptor.forClass(Stock.class);
        Mockito.verify(stocksRepository).save(captor.capture());
        var stock1 = captor.getValue();
        assertThat(stock1.id).isEqualTo(id);
        assertThat(stock1.amount).isEqualTo(amount0 + amount);
        assertThat(stock1.price).isEqualTo(price);
    }

    @Test
    public void getStockTest() {
        long stockId = 713571;
        var stock = createStock(175, 138, 88540);

        Mockito.when(stocksRepository.findById(stockId))
                .thenReturn(Optional.of(stock));

        assertThat(stockService.getStock(stockId)).isEqualTo(stock);
    }

    @Test
    public void getStocksTest() {
        var stocks = List.of(
                createStock(816, 151378, 391591),
                createStock(462, 12737, 6727),
                createStock(7, 984940, 642),
                createStock(2, 4400, 732)
        );

        Mockito.when(stocksRepository.findAll())
                        .thenReturn(stocks);

        assertThat(stockService.getStocks()).isEqualTo(stocks);
    }

    @Test
    public void buyStocksTest() {
        long id = 175, price = 88540, amount0 = 13416, amount = 156;

        Mockito.when(stocksRepository.findById(id))
                .thenReturn(Optional.of(createStock(id, amount0, price)));

        stockService.buyStocks(id, amount, price);

        ArgumentCaptor<Stock> captor = ArgumentCaptor.forClass(Stock.class);
        Mockito.verify(stocksRepository).save(captor.capture());
        var stock1 = captor.getValue();
        assertThat(stock1.id).isEqualTo(id);
        assertThat(stock1.amount).isEqualTo(amount0 - amount);
        assertThat(stock1.price).isEqualTo(price);
    }

    @Test
    public void sellStocksTest() {
        long id = 175, price = 88540, amount0 = 13416, amount = 156;

        Mockito.when(stocksRepository.findById(id))
                .thenReturn(Optional.of(createStock(id, amount0, price)));

        stockService.sellStocks(id, amount, price);

        ArgumentCaptor<Stock> captor = ArgumentCaptor.forClass(Stock.class);
        Mockito.verify(stocksRepository).save(captor.capture());
        var stock1 = captor.getValue();
        assertThat(stock1.id).isEqualTo(id);
        assertThat(stock1.amount).isEqualTo(amount0 + amount);
        assertThat(stock1.price).isEqualTo(price);
    }

    @Test
    public void changeStockPriceTest() {
        long id = 175, amount = 156, price = 88540, newPrice = 51313;

        Mockito.when(stocksRepository.findById(id))
                .thenReturn(Optional.of(createStock(id, amount, price)));

        stockService.changeStockPrice(id, newPrice);

        ArgumentCaptor<Stock> captor = ArgumentCaptor.forClass(Stock.class);
        Mockito.verify(stocksRepository).save(captor.capture());
        var stock1 = captor.getValue();
        assertThat(stock1.id).isEqualTo(id);
        assertThat(stock1.amount).isEqualTo(amount);
        assertThat(stock1.price).isEqualTo(newPrice);
    }


    private Stock createStock(long id, long amount, long price) {
        var stock = new Stock();
        stock.id = id;
        stock.amount = amount;
        stock.price = price;
        return stock;
    }
}
