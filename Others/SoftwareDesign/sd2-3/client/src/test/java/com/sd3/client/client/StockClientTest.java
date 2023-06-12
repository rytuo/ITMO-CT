package com.sd3.client.client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd3.client.model.Stock;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class StockClientTest {

    @Container
    public final GenericContainer stock = new GenericContainer<>(
            DockerImageName.parse("stock")
    ).withExposedPorts(8091);

    private StockClient stockClient;

    @BeforeEach
    public void beforeEach() {
        stockClient = new StockClientImpl().setBaseUrl(getBaseUrl());
    }

    @Test
    public void getStockTest() {
        long amount = 13141, price = 75382;
        long stockId = createStock(amount, price);

        var stock = stockClient.getStock(stockId);

        assertThat(stock.id).isEqualTo(stockId);
        assertThat(stock.amount).isEqualTo(amount);
        assertThat(stock.price).isEqualTo(price);
    }

    @Test
    public void buyStockTest() {
        long amount = 9410, price = 148, amount1 = 3;
        long stockId = createStock(amount, price);

        stockClient.buyStock(stockId, amount1, price);

        var stock = getStock(stockId);
        assertThat(stock.id).isEqualTo(stockId);
        assertThat(stock.amount).isEqualTo(amount - amount1);
        assertThat(stock.price).isEqualTo(price);
    }

    @Test
    public void sellStockTest() {
        long amount = 9410, price = 148, amount1 = 3;
        long stockId = createStock(amount, price);

        stockClient.sellStock(stockId, amount1, price);

        var stock = getStock(stockId);
        assertThat(stock.id).isEqualTo(stockId);
        assertThat(stock.amount).isEqualTo(amount + amount1);
        assertThat(stock.price).isEqualTo(price);
    }


    private long createStock(long amount, long price) {
        long companyId = makeRequest("POST", "/createCompany", Collections.emptyMap(), Long.class);
        long stockId = makeRequest("POST", "/createStock", Map.of("companyId", companyId), Long.class);
        makeRequest("POST", "/changeStockPrice", Map.of("stockId", stockId, "newPrice", price), null);
        makeRequest("POST", "/addStocks", Map.of("stockId", stockId, "amount", amount), null);
        return stockId;
    }

    private Stock getStock(long stockId) {
        return makeRequest("GET", "/getStock", Map.of("stockId", stockId), Stock.class);
    }

    private <T> T makeRequest(
            String method,
            String path,
            Map<String, Object> queryParams,
            Class<T> resultClass
    ) {
        HttpURLConnection connection = null;

        String link = getBaseUrl() + path;
        if (!queryParams.isEmpty()) {
            link += "?" + queryParams.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
        }

        try {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            var inputStream = connection.getInputStream();
            if (resultClass == null) {
                return null;
            } else {
                return new ObjectMapper().readValue(inputStream, resultClass);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String getBaseUrl() {
        return "http://" + stock.getHost() + ":" + stock.getFirstMappedPort();
    }
}
