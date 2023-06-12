package two.hashtag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import two.hashtag.vk.VkHashtagClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class HashtagManagerTest {

    private static final String HASHTAG = "#vk";

    private HashtagManager hashtagManager;

    @Mock
    private VkHashtagClient hashtagClient;

    @BeforeEach
    void beforeEach() {
        hashtagManager = new HashtagManager(hashtagClient);
    }

    @Test
    void testGetHashtagMentions() {
        when(hashtagClient.getQueryMentions(eq(HASHTAG), any(Long.class), any(Long.class)))
                .thenReturn(1, 2, 3);

        int[] result = hashtagManager.getHashtagMentions(HASHTAG, 3);

        assertThat(result.length).isEqualTo(3);
        assertThat(result[0]).isEqualTo(1);
        assertThat(result[1]).isEqualTo(2);
        assertThat(result[2]).isEqualTo(3);

        verify(hashtagClient, times(3))
                .getQueryMentions(eq(HASHTAG), any(Long.class), any(Long.class));
    }

    @Test
    void testInvalidHashtag() {
        assertThatThrownBy(() -> hashtagManager.getHashtagMentions(null, 1))
                .isInstanceOf(AssertionError.class);
        assertThatThrownBy(() -> hashtagManager.getHashtagMentions("123", 1))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void testInvalidHours() {
        assertThatThrownBy(() -> hashtagManager.getHashtagMentions(HASHTAG, 0))
                .isInstanceOf(AssertionError.class);
        assertThatThrownBy(() -> hashtagManager.getHashtagMentions(HASHTAG, 25))
                .isInstanceOf(AssertionError.class);
    }
}
