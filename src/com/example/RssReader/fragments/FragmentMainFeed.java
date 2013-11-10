package com.example.RssReader.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.example.RssReader.rss.helper.RSSFeed;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/10/13
 * Time: 12:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class FragmentMainFeed extends Fragment {
    View v;
    ListView rssItemList;
    public RSSFeed feed;
    public String test = "111";
    Context context = getActivity();
    CustomListAdapter rssFeedListAdapter;
    FragmentDetail fragmentDetail;
    WebView webContent;
    TextView url;
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


                if (fragmentDetail != null && fragmentDetail.isInLayout()
                        && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
                    setDetails(url);
                } else {
                    createActionView(url);
                }
                //createActivity(url);
            }

        });

        if (fragmentDetail != null && fragmentDetail.isInLayout()) {
            webContent.getSettings().setJavaScriptEnabled(true);
            webContent.loadUrl(rssFeedListAdapter.getRSSItem(0).getRef());
        }

    }

    private void createActivity(String url) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void createActionView(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void initElement() {
        fragmentDetail = (FragmentDetail) getFragmentManager().findFragmentById(R.id.details_frag);
        if (fragmentDetail != null) {
            //url = (TextView) fragmentDetail.getView().findViewById(R.id.url);
            webContent = (WebView) fragmentDetail.getView().findViewById(R.id.webContent);
            pd = (ProgressBar) fragmentDetail.getView().findViewById(R.id.web_view_progress_bar);

        }
    }

    private void setDetails(String urlString) {
        //url.setText(urlString);
        webContent.getSettings().setJavaScriptEnabled(true);
        webContent.loadUrl(urlString);
//        webContent.setWebViewClient(new WebViewClient());
//        webContent.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if(progress < 100 && pd.getVisibility() == ProgressBar.GONE){
//                    webContent.setVisibility(WebView.GONE);
//                    pd.setVisibility(ProgressBar.VISIBLE);
//                }
//                pd.setProgress(progress);
//                if(progress == 100 && webContent.getVisibility() == WebView.GONE) {
//                    pd.setVisibility(ProgressBar.GONE);
//                    webContent.setVisibility(WebView.VISIBLE);
//                }
//            }
//        });
    }

    public void updateList(RSSFeed feed) {
        //CustomListAdapter rssFeedListAdapter2 = new CustomListAdapter(v.getContext(), feed.getItemlist());
        //rssItemList.setAdapter(rssFeedListAdapter2);
    }

    class ClassWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
