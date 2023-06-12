package two.hashtag.vk;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VkHashtagResponseParserTest {

    private final VkHashtagResponseParser responseParser = new VkHashtagResponseParser();

    private static final String VALID_RESPONSE = "{\"response\": {\"total_count\": 5}}";
    private static final int RESULT = 5;

    private static final String INVALID_RESPONSE = "{\"error\": \"error\"}";

    @Test
    void testValidResponseParse() {
        int result = responseParser.parse(VALID_RESPONSE);
        assertThat(result).isEqualTo(RESULT);
    }

    @Test
    void testParseInvalidResponse() {
        assertThatThrownBy(() -> responseParser.parse(INVALID_RESPONSE))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(INVALID_RESPONSE);
    }
}
