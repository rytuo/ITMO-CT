package ru.akirakozov.sd.refactoring.controller.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.akirakozov.sd.refactoring.entity.Product;

public class SQLResultCollector {

    private static final String PRODUCT_NAME_COLUMN = "name";
    private static final String PRODUCT_PRICE_COLUMN = "price";

    public List<Product> collectProducts(ResultSet resultSet) {
        List<Product> products = new ArrayList<>();
        Product product;
        while((product = collectProduct(resultSet)) != null) {
            products.add(product);
        }
        return products;
    }

    public Product collectProduct(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return null;
            }
            String name = resultSet.getString(PRODUCT_NAME_COLUMN);
            long price = resultSet.getLong(PRODUCT_PRICE_COLUMN);
            return new Product(name, price);
        } catch (SQLException e) {
            throw new RuntimeException("Error while collecting product: " + e.getMessage());
        }
    }

    public Long collectLong(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error while collecting int: " + e.getMessage());
        }
    }
}
