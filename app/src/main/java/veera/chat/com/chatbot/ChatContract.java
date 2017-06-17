package veera.chat.com.chatbot;

import android.net.Uri;
import android.provider.BaseColumns;


public class ChatContract {
    public static final String CONTENT_AUTHORITY = "chatBot";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CHAT_MESSAGE = "chat_messages";

    public static final class ChatMessageEntry implements BaseColumns {

        public static final Uri CHAT_MESSAGE_URI = BASE_CONTENT_URI.buildUpon().
                appendPath(PATH_CHAT_MESSAGE).encodedFragment("chat").build();

        public static final String TABLE_NAME = "chat";

        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_CHAT_BOT_ID = "chat_bot_id";
        public static final String COLUMN_SENT_ON = "sent_on";
        public static final String COLUMN_MESSAGE_TYPE = "type";
        public static final String COLUMN_MESSAGE_STATUS = "message_status"; //// 0 - waiting, 1 - received by server, 2 - not delivered

    }
}
