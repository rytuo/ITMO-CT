package two.hashtag.vk;

import io.github.cdimascio.dotenv.Dotenv;

public interface VkClient {
    String API_URL = "https://api.vk.com/method/";
    String API_VERSION_PARAM = "v";
    String API_VERSION = "5.131";
    String ACCESS_TOKEN_PARAM = "access_token";

    default String getAccessToken() {
        return Dotenv.load().get("ACCESS_TOKEN");
    }
}
