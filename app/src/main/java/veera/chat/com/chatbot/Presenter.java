package veera.chat.com.chatbot;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import veera.chat.com.chatbot.mvp.MVPBasePresenter;


public class Presenter extends MVPBasePresenter<ChatView> {
    private ApiService apiService;

    public Presenter(ApiService apiService) {
        this.apiService = apiService;
    }

    public void sendMessage(String message) {
        Map<String, String> params = new HashMap<>();
        params.put("apiKey", "6nt5d1nJHkqbkphe");
        params.put("chatBotID", "63906");
        params.put("externalID", "chirag1");
        params.put("message", message);
        Call<ChatResponse> sendMessageCall = apiService.sendChatMessage(params);
        sendMessageCall.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (isViewAlive()) {
                    if (response.isSuccessful() && response.body().getSuccess() == 1) {
                        getView().setMessages(response.body().getMessage().getMessage(),1);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {

            }
        });
    }
}
