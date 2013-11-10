package com.example.RssReader;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/9/13
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.InputStream;
import java.io.OutputStream;

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
}
