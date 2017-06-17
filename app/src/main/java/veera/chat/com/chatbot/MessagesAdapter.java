package veera.chat.com.chatbot;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int MESSAGE_SENT = 2;
    private final int MESSAGE_RECEIVED = 1;
    private SparseArray<ChatMessage> messageHashMap;


    public MessagesAdapter() {
        messageHashMap = new SparseArray<>();
    }

    public void setMessages(SparseArray<ChatMessage> messageHashMap) {
        this.messageHashMap = messageHashMap;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messageHashMap.get(position);
        switch (message.getType()) {
            case 1:
                return MESSAGE_RECEIVED;
            case 2:
            case 3:
            case 4:
            case 5:
                return MESSAGE_SENT;
            default:
                return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.p_chat_receive_item, parent, false);
                return new MessageHolder(view);
            case 2:
                view = inflater.inflate(R.layout.p_chat_send_item, parent, false);
                return new MessageHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageHolder) {
            ChatMessage message = messageHashMap.get(position);
            MessageHolder viewHolder = (MessageHolder) holder;
            viewHolder.message.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageHashMap != null ? messageHashMap.size() : 0;
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        TextView message;

        public MessageHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
        }
    }
}
