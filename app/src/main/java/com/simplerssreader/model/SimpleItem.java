package com.simplerssreader.model;

/**
 * A simplified version of {@link Item} that holds only a title and a link.
 *
 * @author Veaceslav Grec
 */
public class SimpleItem {

    private final String title;
    private final String link;

    public SimpleItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
