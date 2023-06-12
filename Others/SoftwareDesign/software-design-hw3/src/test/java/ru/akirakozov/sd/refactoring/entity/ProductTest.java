package ru.akirakozov.sd.refactoring.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    private static final String NAME = "testName1";
    private static final int PRICE = 12345;
    private Product product;

    @BeforeEach
    void beforeEach() {
        product = new Product(NAME, PRICE);
    }

    @Test
    void testInit() {
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getPrice()).isEqualTo(PRICE);
    }

    @Test
    void testMutableName() {
        String newName = "testName2";
        product.setName(newName);
        assertThat(product.getName()).isEqualTo(newName);
        assertThat(product.getPrice()).isEqualTo(PRICE);
    }

    @Test
    void testMutablePrice() {
        int newPrice = 54321;
        product.setPrice(newPrice);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getPrice()).isEqualTo(newPrice);
    }

    @Test
    void testIsSame() {
        String name = "test1";
        long price = 123;
        assertThat(new Product(name, price).isSame(new Product(name, price))).isTrue();
    }

    @Test
    void testNotIsSame() {
        String name = "test1";
        long price = 123;
        assertThat(new Product(name, price).isSame(new Product(name, price + 1))).isFalse();
        assertThat(new Product(name, price).isSame(new Product(name + "1", price))).isFalse();
    }
}
