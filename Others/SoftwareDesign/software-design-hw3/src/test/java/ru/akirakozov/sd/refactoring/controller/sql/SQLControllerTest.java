package ru.akirakozov.sd.refactoring.controller.sql;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ru.akirakozov.sd.refactoring.entity.Product;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SQLControllerTest {

    private static final String DB_URL = "jdbc:sqlite:test.db";

    private SQLExecutor executor;

    private SQLController controller;

    @BeforeAll
    void beforeAll() {
        executor = new SQLExecutor(DB_URL);
        executor.executeUpdate(SQLQueries.INIT.getQuery());
        controller = new SQLController(
                executor,
                new SQLResultCollector()
        );
    }

    @BeforeEach
    void setup() {
        executor.executeUpdate(SQLQueries.DELETE_ALL_PRODUCTS.getQuery());
    }

    @Test
    public void testInit() {
        int changedCount = controller.init();
        assertThat(changedCount).isNotNegative();
    }

    @Test
    public void testAddProducts() {
        List<Product> testProducts = List.of(
                createUniqueProduct(),
                createUniqueProduct()
        );

        int changedCount = controller.addProducts(testProducts);
        assertThat(changedCount).isNotNegative();

        List<Product> actualProducts = controller.getAllProducts();

        assertThat(actualProducts).hasSameSizeAs(testProducts);
        for (var testProduct : testProducts) {
            assertThat(actualProducts).anyMatch(testProduct::isSame);
        }
    }

    @Test
    public void testGetAllProducts() {
        List<Product> testProducts = List.of(
                createUniqueProduct(),
                createUniqueProduct()
        );
        controller.addProducts(testProducts);

        List<Product> actualProducts = controller.getAllProducts();
        assertThat(actualProducts).hasSameSizeAs(testProducts);
        for (var testProduct : testProducts) {
            assertThat(actualProducts).anyMatch(testProduct::isSame);
        }
    }

    @Test
    public void testGetMaxPriceProduct() {
        Product maxProduct = createUniqueProduct();
        maxProduct.setPrice(Long.MAX_VALUE);

        Product minProduct = createUniqueProduct();
        minProduct.setPrice(Long.MIN_VALUE);

        controller.addProducts(List.of(
                maxProduct,
                minProduct
        ));

        List<Product> products = controller.getMaxPriceProduct();
        assertThat(products.size()).isOne();
        assertTrue(maxProduct.isSame(products.get(0)));
    }

    @Test
    public void testGetMinPriceProduct() {
        Product maxProduct = createUniqueProduct();
        maxProduct.setPrice(Long.MAX_VALUE);

        Product minProduct = createUniqueProduct();
        minProduct.setPrice(Long.MIN_VALUE);

        controller.addProducts(List.of(
                maxProduct,
                minProduct
        ));

        List<Product> products = controller.getMinPriceProduct();
        assertThat(products.size()).isOne();
        assertTrue(minProduct.isSame(products.get(0)));
    }

    @Test
    public void testGetPriceSum() {
        Product product1 = createUniqueProduct();
        product1.setPrice(5);

        Product product2 = createUniqueProduct();
        product2.setPrice(3);

        controller.addProducts(List.of(
                product1,
                product2
        ));

        long sum = controller.getPriceSum();
        assertThat(sum).isEqualTo(8);
    }

    @Test
    public void testGetProductsCount() {
        controller.addProducts(List.of(
                createUniqueProduct(),
                createUniqueProduct(),
                createUniqueProduct()
        ));

        long count = controller.getProductsCount();
        assertThat(count).isEqualTo(3);
    }


    private Product createUniqueProduct() {
        long timestamp = System.currentTimeMillis();
        return new Product("test" + timestamp, timestamp);
    }
}
