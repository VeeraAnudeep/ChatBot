package veera.chat.com.chatbot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import veera.chat.com.chatbot.dbManager.dbHelper.BaseDbHelper;

public class ChatDbHelper extends BaseDbHelper {

    public static final String DATABASE_NAME = "doc.db";
    private static final String TAG = ChatDbHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    public ChatDbHelper(Context context) {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }

    public ChatDbHelper(Context context, String dbName) {
        super(context, dbName, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the tables
        final String SQL_CREATE_DOC_TABLE = "CREATE TABLE " + ChatContract.ChatMessageEntry.TABLE_NAME + " (" +
                ChatContract.ChatMessageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ChatContract.ChatMessageEntry.COLUMN_MESSAGE + " INTEGER, " +
                ChatContract.ChatMessageEntry.COLUMN_CHAT_BOT_ID + " TEXT, " +
                ChatContract.ChatMessageEntry.COLUMN_SENT_ON + " TEXT, " +
                ChatContract.ChatMessageEntry.COLUMN_MESSAGE_TYPE+ " TEXT, " +
                ChatContract.ChatMessageEntry.COLUMN_MESSAGE_STATUS + " TEXT);";

        db.execSQL(SQL_CREATE_DOC_TABLE);
    }

    @Override
    public Cursor query(String table, String[] projectionIn, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder, String limit) {
        return super.query(table, projectionIn, selection, selectionArgs, groupBy, having, sortOrder, limit);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChatContract.ChatMessageEntry.TABLE_NAME);
        onCreate(db);
    }
}
