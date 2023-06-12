package two.hashtag.vk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VkHashtagResponseParser {

    private static final String RESPONSE_FIELD = "response";
    private static final String TOTAL_COUNT_FIELD = "total_count";

    public int parse(String responseBody) {
        try {
            JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject response = root.get(RESPONSE_FIELD).getAsJsonObject();
            return response.get(TOTAL_COUNT_FIELD).getAsInt();
        } catch (NullPointerException e) {
            throw new RuntimeException("Invalid answer structure:\n" + responseBody);
        }
    }
}
