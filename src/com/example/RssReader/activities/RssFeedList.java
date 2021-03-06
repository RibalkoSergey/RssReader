package com.example.RssReader.activities;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import com.example.RssReader.R;
import com.example.RssReader.Utils;
import com.example.RssReader.adapters.CustomListAdapter;
import com.example.RssReader.content.provider.ContentProviderHelper;
import com.example.RssReader.fragments.FragmentMainFeed;
import com.example.RssReader.parser.helper.Parser;
import com.example.RssReader.rss.helper.RSSFeed;
import com.example.RssReader.rss.helper.RSSItem;
import com.example.RssReader.services.RssService;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/10/13
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class RssFeedList extends ActionBarActivity {
    private MenuItem menuItem;
    private RSSFeed feed;
    //private PendingIntent pi;
    BroadcastReceiver br;
    public final static String PARAM_PINTENT = "pendingIntent";
    public final static String PARAM_FEED = "feed";
    public final static String BROADCAST_ACTION = "com.example.rssfeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_list);
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                feed = (RSSFeed) intent.getBundleExtra(PARAM_FEED).get("feed");
                FragmentMainFeed fr = (FragmentMainFeed)getSupportFragmentManager().findFragmentById(R.id.list_frag);
                fr.updateList(feed.getItemlist());
            }
        };

        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intFilt);

        Utils.setFrequencyMin(this, 15);

        Intent intent = new Intent(this, RssService.class);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            feed = (RSSFeed) data.getBundleExtra(PARAM_FEED).get("feed");
            FragmentMainFeed fr = (FragmentMainFeed)getSupportFragmentManager().findFragmentById(R.id.list_frag);
            fr.updateList(feed.getItemlist());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, RssService.class);
        stopService(intent);
        unregisterReceiver(br);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setSubtitle("RSS feed");
        bar.setTitle("Корреспондент");
        bar.setIcon(R.drawable.rss_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:

                FragmentMainFeed fr1 = (FragmentMainFeed)getSupportFragmentManager().findFragmentById(R.id.list_frag);
                fr1.updateList((RSSFeed) getIntent().getExtras().get("feed"));

//                menuItem = item;
//                MenuItemCompat.setActionView(menuItem, R.layout.progressbar);
//                //menuItem.setActionView(R.layout.progressbar);
//                MenuItemCompat.expandActionView(menuItem);
//                //menuItem.expandActionView();
//                TestTask task = new TestTask();
//                task.execute("test");
                break;
            case R.id.list:
                ArrayList<RSSItem> list = ContentProviderHelper.getFavouriteList(this);
                FragmentMainFeed fr = (FragmentMainFeed)getSupportFragmentManager().findFragmentById(R.id.list_frag);
                fr.updateList(list);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class TestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Parser myParser = new Parser();
            feed = myParser.parseXml(Utils.RSSFEEDURL);
            FragmentMainFeed frag = (FragmentMainFeed) getSupportFragmentManager().findFragmentById(R.id.list_frag);
            frag.updateList(feed);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            MenuItemCompat.collapseActionView(menuItem);
            MenuItemCompat.setActionView(menuItem, null);
        }
    };
}
