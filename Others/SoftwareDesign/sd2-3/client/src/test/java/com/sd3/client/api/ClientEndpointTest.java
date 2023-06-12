package com.sd3.client.api;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd3.client.model.Stock;
import com.sd3.client.model.UserStats;
import com.sd3.client.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ClientEndpointTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createUserTest() throws Exception {
        long userId = 41621;
        Mockito.when(userService.createUser())
                        .thenReturn(userId);
        mockMvc.perform(post("/createUser"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.equalTo(Long.toString(userId))));
    }

    @Test
    public void addFundsTest() throws Exception {
        long userId = 39148194, funds = 938;
        var params = new LinkedMultiValueMap<String, String>();
        params.add("userId", Long.toString(userId));
        params.add("funds", Long.toString(funds));
        mockMvc.perform(post("/addFunds")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    public void getStatsTest() throws Exception {
        var userStats = new UserStats();
        userStats.userId = 848149;
        userStats.funds = 941099;
        userStats.stocks = List.of(
                createStock(75178, 11489, 91500),
                createStock(113, 3188, 4811900),
                createStock(62, 138818, 4167)
        );
        Mockito.when(userService.getUserStats(userStats.userId))
                .thenReturn(userStats);
        mockMvc.perform(get("/getStats")
                        .param("userId", Long.toString(userStats.userId)))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.equalTo(mapper.writeValueAsString(userStats))));
    }

    @Test
    public void getTotalFundsTest() throws Exception {
        long userId = 848149, total = 7890;
        Mockito.when(userService.getTotalFunds(userId))
                .thenReturn(total);
        mockMvc.perform(get("/getTotalFunds")
                        .param("userId", Long.toString(userId)))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.equalTo(Long.toString(total))));
    }

    @Test
    public void buyStocksTest() throws Exception {
        long userId = 241, stockId = 3, amount = 848, price = 99041;
        var params = new LinkedMultiValueMap<String, String>();
        params.add("userId", Long.toString(userId));
        params.add("stockId", Long.toString(stockId));
        params.add("amount", Long.toString(amount));
        params.add("price", Long.toString(price));
        mockMvc.perform(post("/buyStocks")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    public void sellStocksTest() throws Exception {
        long userId = 241, stockId = 3, amount = 848, price = 99041;
        var params = new LinkedMultiValueMap<String, String>();
        params.add("userId", Long.toString(userId));
        params.add("stockId", Long.toString(stockId));
        params.add("amount", Long.toString(amount));
        params.add("price", Long.toString(price));
        mockMvc.perform(post("/sellStocks")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }


    private Stock createStock(long id, long amount, long price) {
        var stock = new Stock();
        stock.id = id;
        stock.amount = amount;
        stock.price = price;
        return stock;
    }
}
