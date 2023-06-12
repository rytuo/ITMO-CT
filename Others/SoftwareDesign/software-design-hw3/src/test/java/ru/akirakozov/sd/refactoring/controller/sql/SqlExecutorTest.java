package ru.akirakozov.sd.refactoring.controller.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SqlExecutorTest {

    private final String CONNECTION_URL = "123";

    private SQLExecutor executor;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    private MockedStatic<DriverManager> driverManager;

    @BeforeEach
    public void beforeEach() throws SQLException {
        driverManager = Mockito.mockStatic(DriverManager.class);
        driverManager.when(() -> DriverManager.getConnection(any(String.class)))
                .thenReturn(connection);

        Mockito.when(connection.createStatement())
                .thenReturn(statement);

        executor = new SQLExecutor(CONNECTION_URL);
    }

    @AfterEach
    public void afterEach() throws SQLException {
        verify(connection).createStatement();
        driverManager.verify(() -> DriverManager.getConnection(CONNECTION_URL));
        driverManager.close();
    }

    @Test
    public void testExecuteUpdate() throws SQLException {
        String testQuery = "select * from PRODUCT";
        int testUpdateCount = 321;

        when(statement.executeUpdate(testQuery))
                .thenReturn(testUpdateCount);

        int updateCount = executor.executeUpdate(testQuery);

        assertThat(updateCount).isEqualTo(testUpdateCount);
        verify(statement).executeUpdate(testQuery);
    }

    @Test
    public void testExecuteUpdate(@Mock ResultSet resultSetStub) throws SQLException {
        String testQuery = "select * from PRODUCT";

        when(statement.executeQuery(testQuery))
                .thenReturn(resultSetStub);

        executor.executeQuery(testQuery, resultSet -> {
            assertThat(resultSet).isEqualTo(resultSetStub);
            return 0;
        });

        verify(statement).executeQuery(testQuery);
    }
}
