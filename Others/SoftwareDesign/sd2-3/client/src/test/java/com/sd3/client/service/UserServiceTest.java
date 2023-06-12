package com.sd3.client.service;

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

import com.sd3.client.client.StockClient;
import com.sd3.client.model.Stock;
import com.sd3.client.model.UserEntity;
import com.sd3.client.model.UserStock;
import com.sd3.client.repository.UsersRepository;
import com.sd3.client.repository.UsersStocksRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UsersStocksRepository usersStocksRepository;

    @Mock
    private StockClient stockClient;

    private UserService userService;

    @BeforeEach
    void beforeEach() {
        userService = new UserService(
                usersRepository,
                usersStocksRepository,
                stockClient
        );
    }

    @Test
    public void createUserTest() {
        long id = 4567;
        var user = new UserEntity();
        user.id = id;
        user.funds = 7345;
        Mockito.when(usersRepository.save(ArgumentMatchers.any()))
                .thenReturn(user);
        assertThat(userService.createUser()).isEqualTo(id);
    }

    @Test
    public void addFundsTest() {
        long id = 53785, funds0 = 315, funds = 1827;

        var user = new UserEntity();
        user.id = id;
        user.funds = funds0;
        Mockito.when(usersRepository.findById(id))
                .thenReturn((Optional.of(user)));

        userService.addFunds(id, funds);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(usersRepository).save(captor.capture());
        var user1 = captor.getValue();

        assertThat(user1.id).isEqualTo(id);
        assertThat(user1.funds).isEqualTo(funds0 + funds);
    }

    @Test
    public void getUserStatsTest() {
        long userId = 14478, funds = 3818741;
        var user = new UserEntity();
        user.id = userId;
        user.funds = funds;
        Mockito.when(usersRepository.findById(userId))
                .thenReturn((Optional.of(user)));
        Mockito.when(usersStocksRepository.getUserStocksByUserId(userId))
                .thenReturn(List.of(
                        createUserStock(274, 9, 12),
                        createUserStock(275154, 419, 2)
                ));
        Mockito.when(stockClient.getStock(9))
                .thenReturn(createStock(9, 24, 242));
        Mockito.when(stockClient.getStock(419))
                .thenReturn(createStock(419, 314, 131));

        var userStats = userService.getUserStats(userId);

        assertThat(userStats.userId).isEqualTo(userId);
        assertThat(userStats.funds).isEqualTo(funds);
        assertThat(userStats.stocks).hasSize(2);
        assertThat(userStats.stocks.get(0).id).isEqualTo(9);
        assertThat(userStats.stocks.get(0).amount).isEqualTo(12);
        assertThat(userStats.stocks.get(0).price).isEqualTo(242);
        assertThat(userStats.stocks.get(1).id).isEqualTo(419);
        assertThat(userStats.stocks.get(1).amount).isEqualTo(2);
        assertThat(userStats.stocks.get(1).price).isEqualTo(131);
    }

    @Test
    public void getTotalFundsTest() {
        long userId = 14478, funds = 3818741;
        var user = new UserEntity();
        user.id = userId;
        user.funds = funds;
        Mockito.when(usersRepository.findById(userId))
                .thenReturn((Optional.of(user)));
        Mockito.when(usersStocksRepository.getUserStocksByUserId(userId))
                .thenReturn(List.of(
                        createUserStock(274, 9, 12),
                        createUserStock(275154, 419, 2)
                ));
        Mockito.when(stockClient.getStock(9))
                .thenReturn(createStock(9, 24, 242));
        Mockito.when(stockClient.getStock(419))
                .thenReturn(createStock(419, 314, 131));

        assertThat(userService.getTotalFunds(userId)).isEqualTo(funds + 242 * 12 + 131 * 2);
    }

    @Test
    public void buyStocksTest() {
        long userId = 76, stockId = 273, amount = 9, price = 1, funds = 765;

        var user = new UserEntity();
        user.id = userId;
        user.funds = funds;
        Mockito.when(usersRepository.findById(userId))
                .thenReturn((Optional.of(user)));
        Mockito.when(usersStocksRepository.getUserStocksByUserIdAndStockId(userId, stockId))
                        .thenReturn(Optional.empty());

        userService.buyStocks(userId, stockId, amount, price);

        Mockito.verify(stockClient).buyStock(stockId, amount, price);

        var userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(usersRepository).save(userCaptor.capture());
        user = userCaptor.getValue();
        assertThat(user.id).isEqualTo(userId);
        assertThat(user.funds).isEqualTo(funds - price * amount);

        var userStockCaptor = ArgumentCaptor.forClass(UserStock.class);
        Mockito.verify(usersStocksRepository).save(userStockCaptor.capture());
        var userStock = userStockCaptor.getValue();
        assertThat(userStock.userId).isEqualTo(userId);
        assertThat(userStock.stockId).isEqualTo(stockId);
        assertThat(userStock.amount).isEqualTo(amount);
    }

    @Test
    public void sellStocksTest() {
        long userId = 76, stockId = 273, amount0 = 9, amount = 1, price = 1, funds = 765;

        var user = new UserEntity();
        user.id = userId;
        user.funds = funds;
        Mockito.when(usersRepository.findById(userId))
                .thenReturn((Optional.of(user)));
        Mockito.when(usersStocksRepository.getUserStocksByUserIdAndStockId(userId, stockId))
                .thenReturn(Optional.of(createUserStock(userId, stockId, amount0)));

        userService.sellStocks(userId, stockId, amount, price);

        Mockito.verify(stockClient).sellStock(stockId, amount, price);

        var userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(usersRepository).save(userCaptor.capture());
        user = userCaptor.getValue();
        assertThat(user.id).isEqualTo(userId);
        assertThat(user.funds).isEqualTo(funds + price * amount);

        var userStockCaptor = ArgumentCaptor.forClass(UserStock.class);
        Mockito.verify(usersStocksRepository).save(userStockCaptor.capture());
        var userStock = userStockCaptor.getValue();
        assertThat(userStock.userId).isEqualTo(userId);
        assertThat(userStock.stockId).isEqualTo(stockId);
        assertThat(userStock.amount).isEqualTo(amount0 - amount);
    }


    private UserStock createUserStock(long userId, long stockId, long amount) {
        var userStock = new UserStock();
        userStock.userId = userId;
        userStock.stockId = stockId;
        userStock.amount = amount;
        return userStock;
    }

    private Stock createStock(long id, long amount, long price) {
        var stock = new Stock();
        stock.id = id;
        stock.amount = amount;
        stock.price = price;
        return stock;
    }
}
