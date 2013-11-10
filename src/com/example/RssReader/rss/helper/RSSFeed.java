package com.example.RssReader.rss.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/9/13
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class RSSFeed implements Serializable {

    private static final long serialVersionUID = 1L;
    private int itemcount = 0;
    private ArrayList<RSSItem> itemlist;

    public RSSFeed() {
        itemlist = new ArrayList<RSSItem>();
    }

    public void addItem(RSSItem item) {
        itemlist.add(item);
        itemcount++;
    }

    public RSSItem getItem(int location) {
        return itemlist.get(location);
    }

    public int getItemCount() {
        return itemcount;
    }

    public ArrayList<RSSItem> getItemlist() {
        return itemlist;
    }
}
