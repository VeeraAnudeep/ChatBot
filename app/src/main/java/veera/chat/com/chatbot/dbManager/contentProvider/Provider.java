package veera.chat.com.chatbot.dbManager.contentProvider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import veera.chat.com.chatbot.dbManager.ChatDbHelper;
import veera.chat.com.chatbot.dbManager.constants.DBConstants;
import veera.chat.com.chatbot.dbManager.dbHelper.BaseDbHelper;


public class Provider extends ContentProvider {

    private static final String TAG = Provider.class.getSimpleName();
    private ChatDbHelper mChatDbHelper;
    private Context mContext;

    private synchronized BaseDbHelper getDataBaseHelper(Uri uri) {
        switch (uri.getFragment()) {
            default:
                return mChatDbHelper;
        }
    }


    @Override
    public boolean onCreate() {
        mContext = getContext();
        mChatDbHelper = new ChatDbHelper(mContext, "doc.db");

        return true;
    }

    @Nullable
    @Override
    public synchronized Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        BaseDbHelper baseDbHelper = getDataBaseHelper(uri);
        String groupBy = uri.getQueryParameter(DBConstants.QUERY_PARAMETER_GROUP_BY);
        String limit = uri.getQueryParameter("LIMIT");
        String notifyUri = uri.getQueryParameter(DBConstants.NOTIFY_URI);
        Cursor cursor = baseDbHelper.query(Matcher.getTable(uri), projection, selection, selectionArgs, groupBy, null, sortOrder, limit);
        ContentResolver contentResolver = mContext.getContentResolver();
        if (cursor != null) {
            cursor.setNotificationUri(contentResolver, uri);
            if (!TextUtils.isEmpty(notifyUri)) {
                contentResolver.notifyChange(Uri.parse(notifyUri), null);
            }
            return cursor;
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public synchronized Uri insert(Uri uri, ContentValues values) {
        BaseDbHelper baseDbHelper = getDataBaseHelper(uri);
        long _id = baseDbHelper.insert(Matcher.getTable(uri), values);
        notifyURI(uri);
        Uri returnUri = DBConstants.buildIdForInsert(_id);
        return returnUri;
    }

    @Override
    public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {

        BaseDbHelper baseDbHelper = getDataBaseHelper(uri);
        int rowsAffected = baseDbHelper.delete(Matcher.getTable(uri), selection, selectionArgs);
        notifyURI(uri);
        return rowsAffected;
    }

    @Override
    public synchronized int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        BaseDbHelper baseDbHelper = getDataBaseHelper(uri);
        int rowsAffected = baseDbHelper.update(Matcher.getTable(uri), values, selection, selectionArgs);
        notifyURI(uri);
        return rowsAffected;
    }

    @Override
    public synchronized int bulkInsert(Uri uri, ContentValues[] values) {
        BaseDbHelper baseDbHelper = getDataBaseHelper(uri);
        int rowsCreated = baseDbHelper.bulkInsert(Matcher.getTable(uri), values);
        notifyURI(uri);
        return rowsCreated;
    }


    private void notifyURI(Uri uri) {
        String notifyUri = uri.getQueryParameter(DBConstants.NOTIFY_URI);
        if (TextUtils.isEmpty(notifyUri)) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
    }

}
