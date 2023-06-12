package com.sd3.stock.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sd3.stock.model.Stock;
import com.sd3.stock.service.StockService;

@RestController
public class StockEndpoint {

    private final StockService stockService;

    @Autowired
    public StockEndpoint(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/createCompany")
    public long createCompany() {
        return stockService.createCompany();
    }

    @PostMapping("/createStock")
    public long createStock(
            @RequestParam long companyId
    ) {
        return stockService.createStock(companyId);
    }

    @PostMapping("/addStocks")
    public void addStocks(
            @RequestParam long stockId,
            @RequestParam long amount
    ) {
        stockService.addStocks(stockId, amount);
    }

    @GetMapping("/getStock")
    public Stock getStock(
            @RequestParam long stockId
    ) {
        return stockService.getStock(stockId);
    }

    @GetMapping("/getStocks")
    public List<Stock> getStocks() {
        return stockService.getStocks();
    }

    @PostMapping("/buyStocks")
    public void buyStocks(
            @RequestParam long stockId,
            @RequestParam long amount,
            @RequestParam long price
    ) {
        stockService.buyStocks(stockId, amount, price);
    }

    @PostMapping("/sellStocks")
    public void sellStocks(
            @RequestParam long stockId,
            @RequestParam long amount,
            @RequestParam long price
    ) {
        stockService.sellStocks(stockId, amount, price);
    }

    @PostMapping("/changeStockPrice")
    public void changeStockPrice(
            @RequestParam long stockId,
            @RequestParam long newPrice
    ) {
        stockService.changeStockPrice(stockId, newPrice);
    }
}
