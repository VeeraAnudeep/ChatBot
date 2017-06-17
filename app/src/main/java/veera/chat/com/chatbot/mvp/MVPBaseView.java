package veera.chat.com.chatbot.mvp;

public interface MVPBaseView<P> {

    /**
     * returns whether view is accepting requests to update ui
     *
     * @return
     */
    boolean isAlive();

    P getNewPresenter();

}