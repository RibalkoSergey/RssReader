package com.example.RssReader;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/9/13
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static final String RSSFEEDURL = "http://k.img.com.ua/rss/ru/web.xml";

    public static boolean isNetworkAvailable(Context context) {
        Boolean result = false;
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = conMgr.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected())
            result = true;
        return result;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static String getLastUpdateData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.example.RssReader", Context.MODE_PRIVATE);
        return prefs.getString("lastDate", "");
    }

    public static void setLastDateUpdateFeed(Context context, String dateStr) {
        try {
            Date date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH).parse(dateStr);
            SharedPreferences prefs = context.getSharedPreferences("com.example.RssReader", Context.MODE_PRIVATE);
            prefs.edit().putString("lastDate", date.toString()).commit();
        } catch (ParseException e) {
            //Toast.makeText(context, "Error saving last update date", Toast.LENGTH_LONG).show();
        }
    }

    public static void setFrequencyPosition(Context context, int postiton) {
        SharedPreferences prefs = context.getSharedPreferences("com.example.RssReader", Context.MODE_PRIVATE);
        prefs.edit().putInt("frequencyPosition", postiton).commit();
    }

    public static int getFrequencyPosition(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.example.RssReader", Context.MODE_PRIVATE);
        return prefs.getInt("frequencyPosition", 0);
    }

    public static void setFrequencyMin(Context context, int postiton) {
        SharedPreferences prefs = context.getSharedPreferences("com.example.RssReader", Context.MODE_PRIVATE);
        prefs.edit().putInt("frequencyMin", postiton).commit();
    }

    public static int getFrequencyMin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.example.RssReader", Context.MODE_PRIVATE);
        return prefs.getInt("frequencyMin", 0);
    }

}
