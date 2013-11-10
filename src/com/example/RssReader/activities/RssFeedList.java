package com.example.RssReader.activities;


import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import com.example.RssReader.R;
import com.example.RssReader.Utils;
import com.example.RssReader.fragments.FragmentMainFeed;
import com.example.RssReader.parser.helper.Parser;
import com.example.RssReader.rss.helper.RSSFeed;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_list);
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
                menuItem = item;
                MenuItemCompat.setActionView(menuItem, R.layout.progressbar);
                //menuItem.setActionView(R.layout.progressbar);
                MenuItemCompat.expandActionView(menuItem);
                //menuItem.expandActionView();
                TestTask task = new TestTask();
                task.execute("test");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class TestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Simulate something long running
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
