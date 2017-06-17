package veera.chat.com.chatbot;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ChatResponse {

    @SerializedName("success")
    private Integer success;
    @SerializedName("errorMessage")
    private String errorMessage;
    @SerializedName("message")
    private ChatMessage message;
    @SerializedName("data")
    private List<Object> data = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

}


