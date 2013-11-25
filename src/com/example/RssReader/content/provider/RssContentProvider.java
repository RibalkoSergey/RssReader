package com.example.RssReader.content.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/24/13
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class RssContentProvider extends ContentProvider {
    final String LOG_TAG = "myLogs";
    static final String DB_NAME = "newsdb";
    static final int DB_VERSION = 1;
    static final String NEWS_TABLE = "news_table";
    static final String NEWS_ID = "id";
    static final String NEWS_FOTO = "url_photo";
    static final String NEWS_DESCR_ARTICLE = "descr_article";
    static final String NEWS_ARTICLE = "article";

    static final String DB_CREATE = "create table " + NEWS_TABLE + "("
            + NEWS_ID + " integer primary key autoincrement, "
            + NEWS_FOTO + " text, " + NEWS_DESCR_ARTICLE + " text, " + NEWS_ARTICLE + " text" + ");";
    static final String AUTHORITY = "com.example.providers.rssreader";
    static final String NEWS_PATH = "news";
    public static final Uri CONTACT_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + NEWS_PATH);
    static final String NEWS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + NEWS_PATH;

    static final String NEWS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + NEWS_PATH;
    static final int URI_NEWS = 1;
    static final int URI_NEWS_ID = 2;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, NEWS_PATH, URI_NEWS);
        uriMatcher.addURI(AUTHORITY, NEWS_PATH + "/#", URI_NEWS_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                if (!TextUtils.isEmpty(selection)) {
                    selection = NEWS_FOTO + " = '"  + selection + "'";
                }
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = NEWS_DESCR_ARTICLE + " ASC";
                }
                break;
            case URI_NEWS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = NEWS_ID + " = " + id;
                } else {
                    selection = selection + " AND " + NEWS_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(NEWS_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                CONTACT_CONTENT_URI);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                return NEWS_CONTENT_TYPE;
            case URI_NEWS_ID:
                return NEWS_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != URI_NEWS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(NEWS_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                if (!TextUtils.isEmpty(selection)) {
                    selection = NEWS_FOTO + " = '"  + selection + "'";
                }
                break;
            case URI_NEWS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = NEWS_ID + " = " + id;
                } else {
                    selection = selection + " AND " + NEWS_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(NEWS_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                 break;
            case URI_NEWS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = NEWS_ID + " = " + id;
                } else {
                    selection = selection + " AND " + NEWS_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(NEWS_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
