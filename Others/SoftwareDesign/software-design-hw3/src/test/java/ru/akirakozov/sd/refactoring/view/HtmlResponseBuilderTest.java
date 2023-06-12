package ru.akirakozov.sd.refactoring.view;

import java.util.List;

import org.junit.jupiter.api.Test;

import ru.akirakozov.sd.refactoring.entity.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlResponseBuilderTest {

    private final HtmlResponseBuilder responseBuilder = new HtmlResponseBuilder();

    @Test
    public void testCreateGetTemplate() {
        List<Product> products = List.of(
                new Product("1", 4),
                new Product("2", 5),
                new Product("3", 6)
        );
        String template = responseBuilder.createGetTemplate(products);
        for (Product product : products) {
            assertThat(template).contains(getTemplate(product));
        }
        assertTrue(isDocument(template));
    }

    @Test
    public void testCreateMaxTemplate() {
        Product product = new Product("321", 123);
        String template = responseBuilder.createMaxTemplate(product);
        assertThat(template).contains(getTemplate(product));
        assertTrue(isDocument(template));
    }

    @Test
    public void testCreateMinTemplate() {
        Product product = new Product("123", 321);
        String template = responseBuilder.createMinTemplate(product);
        assertThat(template).contains(getTemplate(product));
        assertTrue(isDocument(template));
    }

    @Test
    public void testCreateSumTemplate() {
        long sum = 82;
        String template = responseBuilder.createCountTemplate(sum);
        assertThat(template).contains(Long.toString(sum));
        assertTrue(isDocument(template));
    }

    @Test
    public void testCreateCountTemplate() {
        long count = 213;
        String template = responseBuilder.createCountTemplate(count);
        assertThat(template).contains(Long.toString(count));
        assertTrue(isDocument(template));
    }

    @Test
    public void testCreateUnknownCommandTemplate() {
        String command = "randomCommand";
        String template = responseBuilder.createUnknownCommandTemplate(command);
        assertThat(template).contains(command);
        assertFalse(isDocument(template));
    }

    @Test
    public void testCreateOKTemplate() {
        String template = responseBuilder.createOKTemplate();
        assertThat(template).contains("OK");
        assertFalse(isDocument(template));
    }


    private String getTemplate(Product product) {
        return product.getName() + "\t" + product.getPrice();
    }

    private boolean isDocument(String template) {
        return template.startsWith("<html><body>") && template.endsWith("</body></html>");
    }
}
