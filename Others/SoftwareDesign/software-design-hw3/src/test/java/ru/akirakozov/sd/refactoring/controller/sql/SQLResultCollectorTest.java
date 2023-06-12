package ru.akirakozov.sd.refactoring.controller.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import ru.akirakozov.sd.refactoring.entity.Product;

@ExtendWith(MockitoExtension.class)
public class SQLResultCollectorTest {

    private SQLResultCollector resultCollector;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void beforeEach() {
        resultCollector = new SQLResultCollector();
    }

    @Test
    public void testCollectProducts() throws SQLException {
        List<Product> testProducts = List.of(
                new Product("1", 1),
                new Product("2", 2),
                new Product("3", 3)
        );

        when(resultSet.next())
                .thenReturn(true, true, true, false);
        when(resultSet.getString(any(String.class)))
                .thenReturn(
                        testProducts.get(0).getName(),
                        testProducts.get(1).getName(),
                        testProducts.get(2).getName(),
                        null
                );
        when(resultSet.getLong(any(String.class)))
                .thenReturn(
                        testProducts.get(0).getPrice(),
                        testProducts.get(1).getPrice(),
                        testProducts.get(2).getPrice()
                );

        List<Product> products = resultCollector.collectProducts(resultSet);
        assertThat(products.size()).isEqualTo(testProducts.size());
        for (int i   = 0; i < products.size(); i++) {
            assertThat(products.get(i).getName()).isEqualTo(testProducts.get(i).getName());
            assertThat(products.get(i).getPrice()).isEqualTo(testProducts.get(i).getPrice());
        }
    }

    @Test
    public void testCollectProduct() throws SQLException {
        Product testProduct = new Product("123", 123);

        when(resultSet.next())
                .thenReturn(true);
        when(resultSet.getString(any(String.class)))
                .thenReturn(testProduct.getName());
        when(resultSet.getLong(any(String.class)))
                .thenReturn(testProduct.getPrice());

        Product product = resultCollector.collectProduct(resultSet);
        assertThat(product.getName()).isEqualTo(testProduct.getName());
        assertThat(product.getPrice()).isEqualTo(testProduct.getPrice());
    }

    @Test
    public void testCollectLong() throws SQLException {
        long testInt = 44;
        when(resultSet.next())
                .thenReturn(true);
        when(resultSet.getLong(any(Integer.class)))
                .thenReturn(testInt);

        Long collectedInt = resultCollector.collectLong(resultSet);
        assertThat(collectedInt).isEqualTo(testInt);
    }
}
