package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ru.akirakozov.sd.refactoring.entity.Product;

public class AddProductServletTest extends ServletTest {

    private static final String SERVER_ADD_PRODUCT_PATH = "/add-product";
    private static final String PRODUCT_NAME_PARAM = "name";
    private static final String PRODUCT_PRICE_PARAM = "price";


    @Override
    void addServlet(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(new AddProductServlet(controller, responseBuilder)), SERVER_ADD_PRODUCT_PATH);
    }

    @Test
    void testAddProduct() throws IOException, InterruptedException {
        Product product = createUniqueProduct();
        String uri = getBaseUrl() + SERVER_ADD_PRODUCT_PATH
                + String.format("?%s=%s&%s=%s", PRODUCT_NAME_PARAM, product.getName(), PRODUCT_PRICE_PARAM, product.getPrice());
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        when(controller.addProducts(any()))
                .thenReturn(1);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
        String contentType = response.headers().firstValue("content-type").orElse(null);
        assertThat(contentType).contains("text/html");
        assertThat(response.body().trim()).isEqualTo("OK");

        ArgumentCaptor<List<Product>> productsCaptor = ArgumentCaptor.forClass(List.class);
        verify(controller).addProducts(productsCaptor.capture());
        List<Product> capturedProducts = productsCaptor.getValue();
        assertThat(capturedProducts.size()).isOne();
        assertTrue(product.isSame(capturedProducts.get(0)));
    }

    @Test
    void testNoProductName() throws IOException, InterruptedException {
        long price = 123;
        String uri = getBaseUrl() + SERVER_ADD_PRODUCT_PATH;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .header(PRODUCT_PRICE_PARAM, Long.toString(price))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void testNoProductPrice() throws IOException, InterruptedException {
        String name = "test1";
        String uri = getBaseUrl() + SERVER_ADD_PRODUCT_PATH;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header(PRODUCT_NAME_PARAM, name)
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void testInvalidProductPrice() throws IOException, InterruptedException {
        String name = "test1";
        String price = "abc";
        String uri = getBaseUrl() + SERVER_ADD_PRODUCT_PATH;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .header(PRODUCT_NAME_PARAM, name)
                .header(PRODUCT_PRICE_PARAM, price)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
