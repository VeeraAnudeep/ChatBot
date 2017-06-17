package veera.chat.com.chatbot;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import veera.chat.com.chatbot.mvp.MVPBaseActivity;

public class MainActivity extends MVPBaseActivity<Presenter> implements ChatView, View.OnClickListener {

    RecyclerView recyclerView;
    EditText editText;
    ImageView sendMessage;

    private MessagesAdapter messagesAdapter;
    private SparseArray<ChatMessage> messageSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editText = (EditText) findViewById(R.id.etChat);
        sendMessage = (ImageView) findViewById(R.id.iv_send_msg);
        sendMessage.setOnClickListener(this);
        messagesAdapter = new MessagesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messagesAdapter);
        messageSparseArray = new SparseArray<>();
    }

    @Override
    public Presenter getNewPresenter() {
        ApiService apiService = ServiceHelper.createService(ApiService.class, ServiceHelper.getRetrofitInstance());
        return new Presenter(apiService);
    }

    @Override
    public void setMessages(ChatMessage chatMessage) {
        messageSparseArray.append(messageSparseArray.size(), chatMessage);
        messagesAdapter.setMessages(messageSparseArray);
        recyclerView.getLayoutManager().scrollToPosition(messageSparseArray.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_msg:
                String messageToSend = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(messageToSend)) {
                    ChatMessage message = new ChatMessage();
                    message.setType(2);
                    message.setMessage(messageToSend);
                    setMessages(message);
                    editText.setText("");
                    getPresenter().sendMessage(messageToSend);
                }
                break;
        }
    }
}
