package com.example.RssReader.content.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.example.RssReader.rss.helper.RSSItem;
import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/25/13
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContentProviderHelper {
    static final Uri NEWS_URI = Uri.parse("content://com.example.providers.rssreader/news");

    public static void addToFavourite(Context context, String imageUrl, String descrArticle, String article) {
        ContentValues cv = new ContentValues();
        cv.put("url_photo", imageUrl);
        cv.put("descr_article", descrArticle);
        cv.put("article", article);
        Uri newUri = context.getContentResolver().insert(NEWS_URI, cv);
    }

    public static ArrayList<RSSItem> getFavouriteList(Context context) {
        ArrayList<RSSItem> items = new ArrayList<RSSItem>();
        Cursor cursor = context.getContentResolver().query(NEWS_URI, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RSSItem rssItem = new RSSItem(cursor.getString(2) == null ? "" : cursor.getString(2), "", "", cursor.getString(1), cursor.getString(3));
            items.add(rssItem);
            cursor.moveToNext();
        }
        return items;
    }

    public static int deleteItems(Context context, String imgUrl) {
        return context.getContentResolver().delete(NEWS_URI, imgUrl, null);
    }

    public static boolean isExist(Context context, String imgUrl) {
        Cursor cursor = context.getContentResolver().query(NEWS_URI, null, imgUrl, null, null);
        return (cursor.getCount() > 0);
    }

}
