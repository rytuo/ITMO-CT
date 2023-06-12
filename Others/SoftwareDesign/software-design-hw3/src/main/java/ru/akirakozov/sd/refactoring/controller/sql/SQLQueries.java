package ru.akirakozov.sd.refactoring.controller.sql;

public enum SQLQueries {
    INIT(
            "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)"
    ),
    ADD_PRODUCTS(
            "INSERT INTO PRODUCT (NAME, PRICE) VALUES %s"
    ),
    GET_ALL_PRODUCTS(
            "SELECT * FROM PRODUCT"
    ),
    GET_MAX_PRICE_PRODUCT(
            "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1"
    ),
    GET_MIN_PRICE_PRODUCT(
            "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1"
    ),
    GET_PRICE_SUM(
            "SELECT SUM(price) FROM PRODUCT"
    ),
    GET_PRODUCTS_COUNT(
            "SELECT COUNT(*) FROM PRODUCT"
    ),
    DELETE_ALL_PRODUCTS(
            "DELETE FROM PRODUCT"
    );

    private final String query;

    public String getQuery() {
        return query;
    }

    SQLQueries(String query) {
        this.query = query;
    }
}
