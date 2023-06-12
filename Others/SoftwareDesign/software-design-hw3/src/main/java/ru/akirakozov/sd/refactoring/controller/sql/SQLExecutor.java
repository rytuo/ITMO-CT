package ru.akirakozov.sd.refactoring.controller.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public class SQLExecutor {

    private final String connectionUrl;

    public SQLExecutor(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public <T> T executeQuery(String query, Function<ResultSet, T> callback) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    return callback.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing query: " + e.getMessage());
        }
    }

    public int executeUpdate(String query) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            try (Statement statement = connection.createStatement()) {
                return statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing update: " + e.getMessage());
        }
    }
}
