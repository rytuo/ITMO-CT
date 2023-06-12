package two.hashtag;

import two.hashtag.vk.VkHashtagClient;

public class HashtagManager {

    private static final long SEC_IN_HOUR = 60 * 60;
    private static final long MS_IN_SEC = 1000;

    private final VkHashtagClient hashtagClient;

    public HashtagManager(VkHashtagClient hashtagClient) {
        this.hashtagClient = hashtagClient;
    }

    public int[] getHashtagMentions(String hashtag, int n) {
        assert hashtag != null && hashtag.startsWith("#"): "Hashtag should start with '#'";
        assert n > 0 && n <= 24: "Number of hours should be above zero and less or equal to 24";

        int[] mentions = new int[n];

        long endTimeSec = System.currentTimeMillis() / MS_IN_SEC;
        long startTime = endTimeSec - SEC_IN_HOUR;
        for (int i = 0; i < n; i++, endTimeSec -= SEC_IN_HOUR, startTime -= SEC_IN_HOUR) {
            mentions[i] = hashtagClient.getQueryMentions(hashtag, startTime, endTimeSec);
        }

        return mentions;
    }
}
