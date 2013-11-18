package com.example.RssReader.rss.helper;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/9/13
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class RSSItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String title = null;
    private String ref = null;
    private String date = null;
    private String image = null;
    private String fullText = null;

    public RSSItem(String title, String ref, String date, String image, String fullText) {
        this.title = title;
        this.ref = ref;
        this.date = date;
        this.image = image;
        this.fullText = fullText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRef(String description) {
        this.ref = description;
    }

    public void setDate(String pubdate) {
        date = pubdate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getRef() {
        return ref;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
}
