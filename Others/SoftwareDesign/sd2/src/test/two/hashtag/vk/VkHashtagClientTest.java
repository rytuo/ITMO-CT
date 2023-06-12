package two.hashtag.vk;

import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import two.http.HttpRequestService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class VkHashtagClientTest {

    private static final String QUERY = "testQuery";
    private static final long START_TIME = 51251351;
    private static final long END_TIME = 51251352;
    private static final String BODY_STRING = "body";
    private static final int RESULT = 271641876;

    private VkHashtagClient hashtagClient;

    @Mock
    private HttpRequestService requestService;

    @Mock
    private VkHashtagResponseParser responseParser;

    @Mock
    private HttpResponse<String> response;

    @BeforeEach
    void beforeEach() {
        hashtagClient = new VkHashtagClient(requestService, responseParser);
    }

    @Test
    void testGetQueryMentions() {
        when(requestService.post(any(), any(), any(), any(BodyHandler.class)))
                .thenReturn(response);

        when(response.statusCode()).thenReturn(HttpStatus.OK_200.getStatusCode());
        when(response.body()).thenReturn(BODY_STRING);

        when(responseParser.parse(eq(BODY_STRING))).thenReturn(RESULT);

        int res = hashtagClient.getQueryMentions(QUERY, START_TIME, END_TIME);

        assertThat(res).isEqualTo(RESULT);

        verify(requestService).post(any(), any(), any(), any(BodyHandler.class));
        verify(response).statusCode();
        verify(response).body();
        verify(responseParser).parse(eq(BODY_STRING));
    }

    @Test
    void testBadStatusCode() {
        when(requestService.post(any(), any(), any(), any(BodyHandler.class)))
                .thenReturn(response);

        when(response.statusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode());

        assertThatThrownBy(() -> hashtagClient.getQueryMentions(QUERY, START_TIME, END_TIME))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(HttpStatus.INTERNAL_SERVER_ERROR_500.getReasonPhrase());
    }

    @Test
    void testNullQuery() {
        assertThatThrownBy(() -> hashtagClient.getQueryMentions(null, START_TIME, END_TIME))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void testInvalidTimeRange() {
        assertThatThrownBy(() -> hashtagClient.getQueryMentions(QUERY, -1, END_TIME))
                .isInstanceOf(AssertionError.class);

        assertThatThrownBy(() -> hashtagClient.getQueryMentions(QUERY, START_TIME, START_TIME - 1))
                .isInstanceOf(AssertionError.class);
    }
}
