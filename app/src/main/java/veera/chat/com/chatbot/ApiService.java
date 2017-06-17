package veera.chat.com.chatbot;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface ApiService {

    @GET("/api/chat/")
    Call<ChatResponse> sendChatMessage(@QueryMap Map<String, String> params);
}
