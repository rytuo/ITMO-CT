package com.sd3.client.client;

import com.sd3.client.model.Stock;

public interface StockClient {

    Stock getStock(long stockId);

    void buyStock(long stockId, long amount, long price);

    void sellStock(long stockId, long amount, long price);
}
