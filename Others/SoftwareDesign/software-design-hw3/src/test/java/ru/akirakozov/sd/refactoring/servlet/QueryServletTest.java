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

public class QueryServletTest extends ServletTest {

    private static final String SERVER_QUERY_COMMAND_PATH = "/query";
    private static final String MAX_COMMAND = "?command=max";
    private static final String MIN_COMMAND = "?command=min";
    private static final String SUM_COMMAND = "?command=sum";
    private static final String COUNT_COMMAND = "?command=count";

    @Override
    void addServlet(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(new QueryServlet(controller, responseBuilder)), SERVER_QUERY_COMMAND_PATH);
    }

    @Test
    void testMax() throws IOException, InterruptedException {
        Product product = createUniqueProduct();
        when(controller.getMaxPriceProduct())
                .thenReturn(List.of(product));

        String uri = getBaseUrl() + SERVER_QUERY_COMMAND_PATH + MAX_COMMAND;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        verify(controller).getMaxPriceProduct();
        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
        String contentType = response.headers().firstValue("content-type").orElse(null);
        assertThat(contentType).contains("text/html");
        assertThat(response.body()).contains(product.getName() + "\t" + product.getPrice());
    }

    @Test
    void testMin() throws IOException, InterruptedException {
        Product product = createUniqueProduct();
        when(controller.getMinPriceProduct())
                .thenReturn(List.of(product));

        String uri = getBaseUrl() + SERVER_QUERY_COMMAND_PATH + MIN_COMMAND;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        verify(controller).getMinPriceProduct();
        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
        String contentType = response.headers().firstValue("content-type").orElse(null);
        assertThat(contentType).contains("text/html");
        assertThat(response.body()).contains(product.getName() + "\t" + product.getPrice());
    }

    @Test
    void testSum() throws IOException, InterruptedException {
        long sum = 126412468;
        when(controller.getPriceSum())
                .thenReturn(sum);

        String uri = getBaseUrl() + SERVER_QUERY_COMMAND_PATH + SUM_COMMAND;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        verify(controller).getPriceSum();
        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
        String contentType = response.headers().firstValue("content-type").orElse(null);
        assertThat(contentType).contains("text/html");
        assertThat(response.body()).contains(Long.toString(sum));
    }

    @Test
    void testCount() throws IOException, InterruptedException {
        long count = 47164716;
        when(controller.getProductsCount())
                .thenReturn(count);

        String uri = getBaseUrl() + SERVER_QUERY_COMMAND_PATH + COUNT_COMMAND;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        verify(controller).getProductsCount();
        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
        String contentType = response.headers().firstValue("content-type").orElse(null);
        assertThat(contentType).contains("text/html");
        assertThat(response.body()).contains(Long.toString(count));
    }

    @Test
    void testInvalidCommand() throws IOException, InterruptedException {
        String uri = getBaseUrl() + SERVER_QUERY_COMMAND_PATH + "?command=invalid";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
        String contentType = response.headers().firstValue("content-type").orElse(null);
        assertThat(contentType).contains("text/html");
        assertThat(response.body()).contains("Unknown command: invalid");
    }
}
