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
// Create required instances
            DocumentBuilderFactory dbf;
            dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse the xml
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            // Get all  tags.
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

//                // Get the required elements from each Item
//                for (int j = 1; j < clength; j = j + 2) {
//
//                    Node thisNode = nchild.item(j);
//                    String theString = null;
//                    String nodeName = thisNode.getNodeName();
//
//                        theString = nchild.item(j).getFirstChild().getNodeValue();
//
//                    if (theString != null) {
//                        if ("title".equals(nodeName)) {
//                            // Node name is equals to 'title' so set the Node
//                            // value to the Title in the RSSItem.
//                            item.setTitle(theString);
//                        }
//
//                        else if ("description".equals(nodeName)) {
//                            item.setDescription(theString);
//
////                          Parse the html description to get the image url
////                            String html = theString;
////                            org.jsoup.nodes.Document docHtml = Jsoup.parse(html);
////                            Elements imgEle = docHtml.select("img");
////                            item.setImage(imgEle.attr("src"));
//                        }
//
//                        else if ("pubDate".equals(nodeName)) {
//
//// We replace the plus and zero's in the date with
//// empty string
//                            String formatedDate = theString.replace(" +0000",
//                                    "");
//                            item.setDate(formatedDate);
//                        }
//
//                    }
//                }

// add item to the list
//                feed.addItem(item);
//            }

        } catch (Exception e) {
        }

// Return the final feed once all the Items are added to the RSSFeed
// Object(feed).
        return feed;
    }

}