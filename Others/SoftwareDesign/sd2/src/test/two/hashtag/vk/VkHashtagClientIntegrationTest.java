package two.hashtag.vk;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import two.http.HttpRequestService;

public class VkHashtagClientIntegrationTest {

    private static final String HASHTAG = "#ставка";
    private static final long START_TIME = 1667158559;
    private static final long END_TIME = 1667158561;

    private static final int RESULT = 1;

    private final VkHashtagClient hashtagClient = new VkHashtagClient(
            new HttpRequestService(),
            new VkHashtagResponseParser()
    );

    @Test
    void testGetQueryMentions() {
        int res = hashtagClient.getQueryMentions(HASHTAG, START_TIME, END_TIME);
        assertThat(res).isEqualTo(RESULT);
    }
}
