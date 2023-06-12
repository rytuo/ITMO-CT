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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ru.akirakozov.sd.refactoring.entity.Product;


public class GetProductsServletTest extends ServletTest {

    private static final String SERVER_GET_PRODUCT_PATH = "/get-products";

    @Override
    void addServlet(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(new GetProductsServlet(controller, responseBuilder)), SERVER_GET_PRODUCT_PATH);
    }

    @Test
    void testGetProducts() throws IOException, InterruptedException {
        List<Product> products = List.of(
                createUniqueProduct(),
                createUniqueProduct()
        );
        when(controller.getAllProducts())
                .thenReturn(products);

        String uri = getBaseUrl() + SERVER_GET_PRODUCT_PATH;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        verify(controller).getAllProducts();
        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
        String contentType = response.headers().firstValue("content-type").orElse(null);
        assertThat(contentType).contains("text/html");
        for (var product : products) {
            assertThat(response.body()).contains(product.getName() + "\t" + product.getPrice());
        }
    }
}
