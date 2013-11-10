package com.example.RssReader.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.widget.TextView;
import com.example.RssReader.R;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/10/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetailActivity extends FragmentActivity {
    TextView url;
    WebView webContent;
    String urlTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        initFields();
        getParametr();
        setParametr();
    }

    private void initFields() {
        //url = (TextView) getSupportFragmentManager().findFragmentById(R.id.details_frag).getView().findViewById(R.id.url);
        webContent = (WebView) getSupportFragmentManager().findFragmentById(R.id.details_frag).getView().findViewById(R.id.webContent);
    }

    private void getParametr() {
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            urlTxt = b.getString("url");
        }
    }

    private void setParametr() {
        if (urlTxt != null) {
            url.setText(urlTxt);
            webContent.getSettings().setJavaScriptEnabled(true);
            webContent.loadUrl(urlTxt);
        }
    }

}
