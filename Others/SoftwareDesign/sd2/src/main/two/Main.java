package two;

import java.util.Arrays;

import two.hashtag.HashtagManager;
import two.hashtag.vk.VkHashtagClient;
import two.hashtag.vk.VkHashtagResponseParser;
import two.http.HttpRequestService;

public class Main {
    public static void main(String... args) {
        HashtagManager hashtagManager = new HashtagManager(
                new VkHashtagClient(
                        new HttpRequestService(),
                        new VkHashtagResponseParser()
                )
        );

        int[] mentions = hashtagManager.getHashtagMentions("#ставка", 5);
        System.out.println(Arrays.toString(mentions));
    }
}
