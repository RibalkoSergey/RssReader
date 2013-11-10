package com.example.RssReader.parser.helper;

import com.example.RssReader.rss.helper.RSSFeed;
import com.example.RssReader.rss.helper.RSSItem;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 11/9/13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {
    private RSSFeed feed = new RSSFeed();

    public RSSFeed parseXml(String xml) {

        URL url = null;
        try {
            url = new URL(xml);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        try {
            DocumentBuilderFactory dbf;
            dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nl = doc.getElementsByTagName("item");
            int length = nl.getLength();

            for (int i = 0; i < length; i++) {
                Node currentNode = nl.item(i);

                NodeList nchild = currentNode.getChildNodes();
                int clength = nchild.getLength();

                String title = nchild.item(1).getFirstChild().getNodeValue();
                String date = nchild.item(3).getFirstChild().getNodeValue();
                String ref = nchild.item(13).getFirstChild().getNodeValue();
                String image = nchild.item(11).getFirstChild().getNodeValue().replace("<img src=","").replace(">","").replace("\"", "");

                if (title != null) {
                    RSSItem item = new RSSItem(title, date, ref, image);
                    feed.addItem(item);
                }
            }
        } catch (Exception e) {
        }

        return feed;
    }

}