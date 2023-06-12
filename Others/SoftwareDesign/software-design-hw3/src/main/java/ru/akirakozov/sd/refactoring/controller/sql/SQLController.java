package ru.akirakozov.sd.refactoring.controller.sql;

import java.util.List;
import java.util.stream.Collectors;

import ru.akirakozov.sd.refactoring.controller.Controller;
import ru.akirakozov.sd.refactoring.entity.Product;

public class SQLController implements Controller {

    private final SQLExecutor sqlExecutor;
    private final SQLResultCollector collector;

    public SQLController(SQLExecutor sqlExecutor, SQLResultCollector collector) {
        this.sqlExecutor = sqlExecutor;
        this.collector = collector;
    }

    @Override
    public int init() {
        return sqlExecutor.executeUpdate(SQLQueries.INIT.getQuery());
    }

    @Override
    public int addProducts(List<Product> products) {
        if (products.isEmpty()) {
            return 0;
        }

        String valuesQuery = products.stream()
                .map(product -> String.format("(\"%s\", %s)", product.getName(), product.getPrice()))
                .collect(Collectors.joining(", "));

        String query = String.format(SQLQueries.ADD_PRODUCTS.getQuery(), valuesQuery);
        return sqlExecutor.executeUpdate(query);
    }

    @Override
    public List<Product> getAllProducts() {
        return sqlExecutor.executeQuery(
                SQLQueries.GET_ALL_PRODUCTS.getQuery(),
                collector::collectProducts
        );
    }

    @Override
    public List<Product> getMaxPriceProduct() {
        return sqlExecutor.executeQuery(
                SQLQueries.GET_MAX_PRICE_PRODUCT.getQuery(),
                collector::collectProducts
        );
    }

    @Override
    public List<Product> getMinPriceProduct() {
        return sqlExecutor.executeQuery(
                SQLQueries.GET_MIN_PRICE_PRODUCT.getQuery(),
                collector::collectProducts
        );
    }

    @Override
    public Long getPriceSum() {
        return sqlExecutor.executeQuery(
                SQLQueries.GET_PRICE_SUM.getQuery(),
                collector::collectLong
        );
    }

    @Override
    public Long getProductsCount() {
        return sqlExecutor.executeQuery(
                SQLQueries.GET_PRODUCTS_COUNT.getQuery(),
                collector::collectLong
        );
    }
}
