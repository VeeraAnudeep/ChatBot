package veera.chat.com.chatbot;

import veera.chat.com.chatbot.mvp.MVPBaseView;


public interface ChatView extends MVPBaseView<Presenter> {
    void setMessages(ChatMessage chatMessage);
}
