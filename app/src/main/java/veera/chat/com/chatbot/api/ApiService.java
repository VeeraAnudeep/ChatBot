package veera.chat.com.chatbot.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import veera.chat.com.chatbot.ChatResponse;


public interface ApiService {

    @GET("/api/chat/")
    Call<ChatResponse> sendChatMessage(@QueryMap Map<String, String> params);
}
