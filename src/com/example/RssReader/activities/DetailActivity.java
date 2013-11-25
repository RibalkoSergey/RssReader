package com.example.RssReader.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.RssReader.R;
import com.example.RssReader.content.provider.ContentProviderHelper;
import com.example.RssReader.loader.helper.ImageLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/10/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetailActivity extends ActionBarActivity {
    private MenuItem menuItem;
    ActionBar bar;
    ImageView img;
    TextView fullTextV;
    String fullText;
    String imgUrl;
    String descrArticle;
    ImageLoader imageLoader;
    Boolean itemIsExist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        if (savedInstanceState == null) {
            imageLoader = new ImageLoader(this);
            initFields();
            getParametr();
            setParametr();
        } else {
            finish();
        }

    }

    private void initFields() {
        bar = getSupportActionBar();
        fullTextV = (TextView) getSupportFragmentManager().findFragmentById(R.id.details_frag).getView().findViewById(R.id.fullText);
        img = (ImageView) getSupportFragmentManager().findFragmentById(R.id.details_frag).getView().findViewById(R.id.imgDetail);

    }

    private void getParametr() {
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            fullText = b.getString("fullText");
            imgUrl = b.getString("img");
            descrArticle = b.getString("descrArticle");
        }
    }

    private void setParametr() {
        itemIsExist = ContentProviderHelper.isExist(this, imgUrl);
        if (fullText != null)
            fullTextV.setText(fullText);
        if (img != null)
            imageLoader.DisplayImage(imgUrl, img);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setSubtitle("RSS feed");
        bar.setTitle("Корреспондент");
        bar.setIcon(R.drawable.rss_menu);

        MenuItem icon = menu.findItem(R.id.addToFavourite);
        if (itemIsExist) {
            icon.setIcon(R.drawable.star_delete);
        } else {
            icon.setIcon(R.drawable.star);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addToFavourite:
                if (itemIsExist) {
                    ContentProviderHelper.deleteItems(this, imgUrl);
                } else {
                    ContentProviderHelper.addToFavourite(this, imgUrl, descrArticle, fullText);
                    item.setIcon(R.drawable.star_off);
                }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
