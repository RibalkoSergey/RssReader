package com.example.RssReader.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.RssReader.R;

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
public class DetailActivity extends FragmentActivity {
    ImageView img;
    TextView fullTextV;
    String fullText;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        initFields();
        getParametr();
        setParametr();
    }

    private void initFields() {
        fullTextV = (TextView) getSupportFragmentManager().findFragmentById(R.id.details_frag).getView().findViewById(R.id.fullText);
        img = (ImageView) getSupportFragmentManager().findFragmentById(R.id.details_frag).getView().findViewById(R.id.imgDetail);

    }

    private void getParametr() {
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            fullText = b.getString("fullText");
            imgUrl = b.getString("img");
        }
    }

    private void setParametr() {
        if (fullText != null) {
            fullTextV.setText(fullText);
        }
        if (img != null) {
            URL url = null;
            try {
                url = new URL(imgUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            img.setImageBitmap(bmp);
        }
    }

}
