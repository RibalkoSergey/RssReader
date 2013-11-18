package com.example.RssReader.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;
import com.example.RssReader.R;
import com.example.RssReader.Utils;
import com.example.RssReader.activities.RssFeedList;
import com.example.RssReader.parser.helper.Parser;
import com.example.RssReader.rss.helper.RSSFeed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/17/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RssService extends Service {
    private Timer timer = new Timer();
    private long TIMER_START_DELAY = 10000L;
    private RSSFeed feed;
    private Parser myParser = new Parser();
    private PendingIntent pi;
    public static NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyFeedShow(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pi = intent.getParcelableExtra(RssFeedList.PARAM_PINTENT);
        //Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(Utils.isNetworkAvailable(RssService.this)){
                    feed = myParser.parseXml(Utils.RSSFEEDURL);
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("feed", feed);
                        Intent intent = new Intent().putExtra(RssFeedList.PARAM_FEED, bundle);
                        pi.send(RssService.this, 100, intent);
                        setLastDateUpdateFeed(feed.getItemlist().get(1).getDate());
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, TIMER_START_DELAY, 3000000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void notifyFeedShow(Context context){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.rss_menu)
                        .setContentTitle("Korespondent RSS is enabled.")
                        .setContentText("Korespondent RSS is enabled.");

        Intent resultIntent = new Intent(context, RssFeedList.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(0, builder.build());
    }

    private void setLastDateUpdateFeed(String data) {
        String string = feed.getItemlist().get(1).getDate().substring(5, 25);
        Date date = null;
        try {
            date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH).parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = this.getSharedPreferences("com.example.RssReader", Context.MODE_PRIVATE);
        prefs.edit().putString("lastDate", data.toString()).commit();
    }
}