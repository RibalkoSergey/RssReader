package com.example.RssReader.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.example.RssReader.R;
import com.example.RssReader.activities.DetailActivity;
import com.example.RssReader.adapters.CustomListAdapter;
import com.example.RssReader.loader.helper.ImageLoader;
import com.example.RssReader.rss.helper.RSSFeed;
import com.example.RssReader.rss.helper.RSSItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/10/13
 * Time: 12:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class FragmentMainFeed extends Fragment {
    View v;
    public ListView rssItemList;
    public RSSFeed feed;
    public String test = "111";
    Context context = getActivity();
    public CustomListAdapter rssFeedListAdapter;
    FragmentDetail fragmentDetail;
    WebView webContent;
    TextView fullTextV;
    ImageView imgV;
    ProgressBar pd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_items, null);
        return v;
    }

    public void updateList(ArrayList<RSSItem> feed) {
        rssFeedListAdapter = new CustomListAdapter(v.getContext(), feed);
        rssItemList.setAdapter(rssFeedListAdapter);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initElement();
        rssItemList = (ListView)v.findViewById(R.id.rssList);
        feed = (RSSFeed) getActivity().getIntent().getExtras().get("feed");

        rssFeedListAdapter = new CustomListAdapter(v.getContext(), feed.getItemlist());
        rssItemList.setAdapter(rssFeedListAdapter);

        rssItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                String url = rssFeedListAdapter.getRSSItem(position).getRef();
                String img = rssFeedListAdapter.getRSSItem(position).getImage();
                String fullText = rssFeedListAdapter.getRSSItem(position).getFullText();

                if (fragmentDetail != null && fragmentDetail.isInLayout()
                        && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
                    setDetails(img, fullText);
                } else {
                    createActivity(img, fullText);
                }
            }

        });

        if (fragmentDetail != null && fragmentDetail.isInLayout()
                && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            setDetails(rssFeedListAdapter.getRSSItem(0).getImage(), rssFeedListAdapter.getRSSItem(0).getFullText());
        }
    }

    private void createActivity(String img, String fullText) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("img", img);
        bundle.putString("fullText", fullText);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void initElement() {
        //fragmentDetail = (FragmentDetail) getFragmentManager().findFragmentById(R.id.details_frag);
        //if (fragmentDetail != null) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentDetail = (FragmentDetail) getFragmentManager().findFragmentById(R.id.details_frag);
            if (fragmentDetail != null) {
                fullTextV = (TextView) fragmentDetail.getView().findViewById(R.id.fullText);
                imgV = (ImageView) fragmentDetail.getView().findViewById(R.id.imgDetail);
            }
        }
    }

    private void setDetails(String img, String fullText) {
        ImageLoader imageLoader = new ImageLoader(getActivity());
        fullTextV.setText(fullText);
        imageLoader.DisplayImage(img, imgV);
//        URL url = null;
//        try {
//            url = new URL(img);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        imgV.setImageBitmap(bmp);

    }

    public void updateList(RSSFeed feed) {
        //CustomListAdapter rssFeedListAdapter2 = new CustomListAdapter(v.getContext(), feed.getItemlist());
        //rssItemList.setAdapter(rssFeedListAdapter2);
    }
}
