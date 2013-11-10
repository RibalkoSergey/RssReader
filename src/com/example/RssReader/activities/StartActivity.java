package com.example.RssReader.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import com.example.RssReader.R;
import com.example.RssReader.Utils;
import com.example.RssReader.parser.helper.Parser;
import com.example.RssReader.rss.helper.RSSFeed;



public class StartActivity extends Activity {
    RSSFeed feed;
    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        if (Utils.isNetworkAvailable(this)) {
            new AsyncLoadXMLFeed().execute();
        } else {
            showMessage();
        }
    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Unable to reach server, \nPlease check your connectivity.")
                .setTitle("TD RSS Reader")
                .setCancelable(false)
                .setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private class AsyncLoadXMLFeed extends AsyncTask {
        @Override
        protected void onPostExecute(Object result) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("feed", feed);

            Intent intent = new Intent();
            intent.setClass(context, RssFeedList.class);
            intent.putExtras(bundle);
            startActivity(intent);

            finish();
        }

        @Override
        protected Object doInBackground(Object... params) {
            Parser myParser = new Parser();
            feed = myParser.parseXml(Utils.RSSFEEDURL);
            return null;
        }
    }
}
