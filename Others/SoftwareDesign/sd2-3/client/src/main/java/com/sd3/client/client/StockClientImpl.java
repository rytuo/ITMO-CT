package com.sd3.client.client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd3.client.model.Stock;

@Service
public class StockClientImpl implements StockClient {

    private String baseUrl = "http://localhost:8091";

    public StockClientImpl setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Stock getStock(long stockId) {
        return makeRequest(
                "GET",
                "/getStock",
                Map.of("stockId", stockId),
                Stock.class
        );
    }

    public void buyStock(long stockId, long amount, long price) {
        makeRequest(
                "POST",
                "/buyStocks",
                Map.of(
                        "stockId", stockId,
                        "amount", amount,
                        "price", price
                ),
                null
        );
    }

    public void sellStock(long stockId, long amount, long price) {
        makeRequest(
                "POST",
                "/sellStocks",
                Map.of(
                        "stockId", stockId,
                        "amount", amount,
                        "price", price
                ),
                null
        );
    }

    private <T> T makeRequest(
            String method,
            String path,
            Map<String, Object> queryParams,
            Class<T> resultClass
    ) {
        HttpURLConnection connection = null;

        String link = baseUrl + path;
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
                return objectMapper.readValue(inputStream, resultClass);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
