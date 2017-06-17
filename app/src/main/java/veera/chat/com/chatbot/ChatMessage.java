package veera.chat.com.chatbot;

import com.google.gson.annotations.SerializedName;


public class ChatMessage {

    @SerializedName("chatBotName")
    private String chatBotName;
    @SerializedName("chatBotID")
    private Integer chatBotID;
    @SerializedName("message")
    private String message;
    @SerializedName("emotion")
    private String emotion;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public Integer getChatBotID() {
        return chatBotID;
    }

    public void setChatBotID(Integer chatBotID) {
        this.chatBotID = chatBotID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

}
