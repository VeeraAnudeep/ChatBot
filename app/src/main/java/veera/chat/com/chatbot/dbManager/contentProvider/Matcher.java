package veera.chat.com.chatbot.dbManager.contentProvider;

import android.content.UriMatcher;
import android.net.Uri;

import veera.chat.com.chatbot.dbManager.ChatContract;
import veera.chat.com.chatbot.dbManager.constants.DBConstants;

public class Matcher {
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(DBConstants.CONTENT_AUTHORITY, ChatContract.PATH_CHAT_MESSAGE, DBConstants.CHAT_MESSAGES);
    }

    public static synchronized String getTable(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case DBConstants.CHAT_MESSAGES:
                return ChatContract.ChatMessageEntry.TABLE_NAME;
        }
        return null;
    }
}
