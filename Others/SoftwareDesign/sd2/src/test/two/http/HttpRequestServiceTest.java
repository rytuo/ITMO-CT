package two.http;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.post;
import static com.xebialabs.restito.semantics.Condition.withHeader;
import static com.xebialabs.restito.semantics.Condition.withPostBodyContaining;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestServiceTest {

    private static final String BASE_SERVER_URL = "http://localhost:";
    private static final String METHOD_PATH = "/abc";
    private static final String REQUEST_CONTENT = "123";
    private static final String RESPONSE_CONTENT = "321";
    private static final String TEST_HEADER_NAME = "header1";
    private static final String TEST_HEADER_VALUE = "header2";

    private final StubServer server = new StubServer();

    @BeforeEach
    void beforeAll() {
        server.run();
    }

    @AfterEach
    void afterAll() {
        server.stop();
    }

    @Test
    void testGet() {
        Condition[] conditions = new Condition[]{
                post(METHOD_PATH),
                withHeader(TEST_HEADER_NAME, TEST_HEADER_VALUE),
                withPostBodyContaining(REQUEST_CONTENT)
        };

        whenHttp(server).match(conditions)
                .then(ok(), stringContent(RESPONSE_CONTENT));

        HttpRequestService requestService = new HttpRequestService();
        HttpResponse<String> response = requestService.post(
                getUrl(),
                Map.of(TEST_HEADER_NAME, TEST_HEADER_VALUE),
                HttpRequest.BodyPublishers.ofString(REQUEST_CONTENT),
                BodyHandlers.ofString()
        );

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK_200.getStatusCode());
        assertThat(response.body()).isEqualTo(RESPONSE_CONTENT);

        verifyHttp(server).once(conditions);
    }

    private String getUrl() {
        return BASE_SERVER_URL + server.getPort() + METHOD_PATH;
    }
}
