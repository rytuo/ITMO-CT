package two.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.time.Duration;
import java.util.Map;

public class HttpRequestService {

    private static final long TIMEOUT_MS = 5000;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public <T> HttpResponse<T> post(
            String url,
            Map<String, String> headers,
            BodyPublisher bodyPublisher,
            BodyHandler<T> bodyHandler
    ) {
        HttpRequest.Builder requestBuilder = HttpRequest
                .newBuilder(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .POST(bodyPublisher)
                .timeout(Duration.ofMillis(TIMEOUT_MS));

        for (var header : headers.entrySet()) {
            requestBuilder.header(header.getKey(), header.getValue());
        }

        try {
            return httpClient.send(requestBuilder.build(), bodyHandler);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
