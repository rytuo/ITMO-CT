package com.sd3.stock.api;

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
import com.sd3.stock.model.Stock;
import com.sd3.stock.service.StockService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StockEndpointTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createCompanyTest() throws Exception {
        long id = 5132513;
        Mockito.when(stockService.createCompany())
                        .thenReturn(id);
        mockMvc.perform(post("/createCompany"))
                .andExpect(status().isOk())
                .andExpect(content().string(Long.toString(id)));
    }

    @Test
    public void createStockTest() throws Exception {
        long companyId = 1839183, stockId = 82738;
        Mockito.when(stockService.createStock(companyId))
                        .thenReturn(stockId);
        mockMvc.perform(post("/createStock")
                        .param("companyId", Long.toString(companyId)))
                .andExpect(status().isOk())
                .andExpect(content().string(Long.toString(stockId)));
    }

    @Test
    public void addStocksTest() throws Exception {
        long stockId = 39148194, amount = 938;
        var params = new LinkedMultiValueMap<String, String>();
        params.add("stockId", Long.toString(stockId));
        params.add("amount", Long.toString(amount));
        mockMvc.perform(post("/addStocks").params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    public void getStockTest() throws Exception {
        var stock = createStock(4775, 48728, 32832);
        Mockito.when(stockService.getStock(stock.id))
                .thenReturn(stock);
        mockMvc.perform(get("/getStock")
                        .param("stockId", Long.toString(stock.id)))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(stock)));
    }

    @Test
    public void getStocksTest() throws Exception {
        List<Stock> stocks = List.of(
                createStock(8278, 92821, 22122),
                createStock(7543, 90092, 62551),
                createStock(34567, 28727, 1515)
        );
        Mockito.when(stockService.getStocks())
                .thenReturn(stocks);
        mockMvc.perform(get("/getStocks"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(stocks)));
    }

    @Test
    public void buyStocksTest() throws Exception {
        var params = new LinkedMultiValueMap<String, String>();
        params.add("stockId", Long.toString(776));
        params.add("amount", Long.toString(52));
        params.add("price", Long.toString(262));
        mockMvc.perform(post("/buyStocks")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    public void sellStocksTest() throws Exception {
        var params = new LinkedMultiValueMap<String, String>();
        params.add("stockId", Long.toString(1234));
        params.add("amount", Long.toString(421));
        params.add("price", Long.toString(12132));
        mockMvc.perform(post("/sellStocks")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.emptyString()));
    }

    @Test
    public void changeStockPriceTest() throws Exception {
        var params = new LinkedMultiValueMap<String, String>();
        params.add("stockId", Long.toString(31813718));
        params.add("newPrice", Long.toString(3718371));
        mockMvc.perform(post("/changeStockPrice")
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
