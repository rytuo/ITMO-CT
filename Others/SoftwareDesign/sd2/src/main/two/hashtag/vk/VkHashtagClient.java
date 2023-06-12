package two.hashtag.vk;

import java.net.URLEncoder;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.glassfish.grizzly.http.util.HttpStatus;

import two.http.HttpRequestService;

public class VkHashtagClient implements VkClient {

    // FYI vk has limit on api requests: 5 per second
    private static final long SLEEP_TIMEOUT = 200;

    private static final String CONTENT_TYPE_HEADER = "content-type";
    private static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";

    private static final String NEWSFEED_SEARCH_METHOD = "newsfeed.search";

    private static final String QUERY_PARAM = "q";
    private static final String START_TIME_PARAM = "start_time";
    private static final String END_TIME_PARAM = "end_time";

    private final HttpRequestService requestService;

    private final VkHashtagResponseParser responseParser;

    public VkHashtagClient(
            HttpRequestService requestService,
            VkHashtagResponseParser responseParser
    ) {
        this.requestService = requestService;
        this.responseParser = responseParser;
    }

    public int getQueryMentions(String query, long startUnixTimeSec, long endUnixTimeSec) {
        assert query != null: "Query should not be null";
        assert startUnixTimeSec > 0 && startUnixTimeSec < endUnixTimeSec:
                "Invalid time range, should be [start, end) where 0 < start < end";

        BodyPublisher publisher = BodyPublishers.ofString(makeParamsQuery(Map.of(
                QUERY_PARAM, query,
                START_TIME_PARAM, Long.toString(startUnixTimeSec),
                END_TIME_PARAM, Long.toString(endUnixTimeSec),
                API_VERSION_PARAM, API_VERSION,
                ACCESS_TOKEN_PARAM, getAccessToken()
        )));

        HttpResponse<String> response = requestService.post(
                API_URL + NEWSFEED_SEARCH_METHOD,
                Map.of(CONTENT_TYPE_HEADER, CONTENT_TYPE_URLENCODED),
                publisher,
                BodyHandlers.ofString()
        );
        sleep();

        HttpStatus status = HttpStatus.getHttpStatus(response.statusCode());
        if (status != HttpStatus.OK_200) {
            throw new RuntimeException("Unsuccessful request " + status.getStatusCode() + " "
                    + status.getReasonPhrase());
        }
        return responseParser.parse(response.body());
    }

    private String makeParamsQuery(Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .collect(Collectors.joining("&"));
    }

    private String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIMEOUT);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
