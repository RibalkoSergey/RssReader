package com.example.RssReader.adapters;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.RssReader.R;
import com.example.RssReader.loader.helper.ImageLoader;
import com.example.RssReader.rss.helper.RSSItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/10/13
 * Time: 12:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    public ArrayList<RSSItem> objects;
    public ImageLoader imageLoader;

    public CustomListAdapter(Context context, ArrayList<RSSItem> rssItemList) {
        ctx = context;
        objects = rssItemList;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        int pos = position;
        if (listItem == null)
            listItem = lInflater.inflate(R.layout.feed_item, null);

        ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
        TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
        TextView tvDate = (TextView) listItem.findViewById(R.id.date);

        RSSItem item = getRSSItem(position);

        imageLoader.DisplayImage(item.getImage(), iv);
        tvTitle.setText(item.getTitle());
        tvDate.setText(item.getDate());

        return listItem;
    }

    public RSSItem getRSSItem(int position) {
        return ((RSSItem) getItem(position));
    }
}
