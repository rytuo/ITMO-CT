package com.sd3.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sd3.client.client.StockClient;
import com.sd3.client.model.UserEntity;
import com.sd3.client.model.UserStats;
import com.sd3.client.model.UserStock;
import com.sd3.client.repository.UsersRepository;
import com.sd3.client.repository.UsersStocksRepository;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final UsersStocksRepository usersStocksRepository;
    private final StockClient stockClient;

    @Autowired
    public UserService(
            UsersRepository usersRepository,
            UsersStocksRepository usersStocksRepository,
            StockClient stockClient
    ) {
        this.usersRepository = usersRepository;
        this.usersStocksRepository = usersStocksRepository;
        this.stockClient = stockClient;
    }

    public long createUser() {
        var user = new UserEntity();
        user = usersRepository.save(user);
        return user.id;
    }

    public void addFunds(long userId, long funds) {
        if (funds < 1) {
            throw new RuntimeException("You may add only positive amount of funds");
        }

        var user = getUser(userId);
        user.funds += funds;
        usersRepository.save(user);
    }

    @Transactional
    public UserStats getUserStats(long userId) {
        var user = getUser(userId);

        var userStats = new UserStats();
        userStats.userId = userId;
        userStats.funds = user.funds;

        List<UserStock> userStocks = usersStocksRepository.getUserStocksByUserId(userId);
        userStats.stocks = userStocks.stream()
                .map(userStock -> {
                    var stockInfo = stockClient.getStock(userStock.stockId);
                    stockInfo.amount = userStock.amount;
                    return stockInfo;
                })
                .toList();

        return userStats;
    }

    public long getTotalFunds(long userId) {
        var userStats = getUserStats(userId);
        return userStats.funds + userStats.stocks.stream()
                .map(stock -> stock.amount * stock.price)
                .reduce(0L, Long::sum);
    }

    @Transactional
    public void buyStocks(long userId, long stockId, long amount, long price) {
        var user = getUser(userId);
        if (user.funds < price * amount) {
            throw new RuntimeException("Not enough funds");
        }

        stockClient.buyStock(stockId, amount, price);

        user.funds -= price * amount;

        var userStock = usersStocksRepository.getUserStocksByUserIdAndStockId(userId, stockId)
                .orElseGet(() -> {
                    var userStock1 = new UserStock();
                    userStock1.userId = userId;
                    userStock1.stockId = stockId;
                    userStock1.amount = 0;
                    return userStock1;
                });
        userStock.amount += amount;

        usersRepository.save(user);
        usersStocksRepository.save(userStock);
    }

    @Transactional
    public void sellStocks(long userId, long stockId, long amount, long price) {
        var user = getUser(userId);
        var userStock = usersStocksRepository.getUserStocksByUserIdAndStockId(userId, stockId)
                .orElseThrow(() -> new RuntimeException("No such stock"));
        if (userStock.amount < amount) {
            throw new RuntimeException("Not enough stocks");
        }

        stockClient.sellStock(stockId, amount, price);

        user.funds += amount * price;
        userStock.amount -= amount;

        usersRepository.save(user);
        if (userStock.amount == 0) {
            usersStocksRepository.delete(userStock);
        } else {
            usersStocksRepository.save(userStock);
        }
    }


    private UserEntity getUser(long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No such user found"));
    }
}
