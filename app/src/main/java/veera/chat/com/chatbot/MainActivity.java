package veera.chat.com.chatbot;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import veera.chat.com.chatbot.mvp.MVPBaseActivity;

public class MainActivity extends MVPBaseActivity<Presenter> implements ChatView, View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

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
        getSupportLoaderManager().initLoader(2, null, this);
    }

    @Override
    public Presenter getNewPresenter() {
        ApiService apiService = ServiceHelper.createService(ApiService.class, ServiceHelper.getRetrofitInstance());
        return new Presenter(apiService);
    }

    @Override
    public void setMessages(String chatMessage, int type) {
        ContentValues values = new ContentValues();
        values.put(ChatContract.ChatMessageEntry.COLUMN_MESSAGE, chatMessage);
        values.put(ChatContract.ChatMessageEntry.COLUMN_MESSAGE_TYPE, type);
        getContentResolver().insert(ChatContract.ChatMessageEntry.CHAT_MESSAGE_URI, values);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_msg:
                String messageToSend = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(messageToSend)) {
                    setMessages(messageToSend, 2);
                    editText.setText("");
                    getPresenter().sendMessage(messageToSend);
                }
                break;
        }
    }

    @Override
    public void messageNotSent() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ChatContract.ChatMessageEntry.CHAT_MESSAGE_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            return;
        }
        messagesAdapter.setMessages(data);
        recyclerView.getLayoutManager().scrollToPosition(data.getCount() - 1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
