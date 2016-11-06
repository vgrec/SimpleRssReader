package com.simplerssreader.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Author vgrec, on 09.07.16.
 */
@Root(name = "rss")
public class Rss {

    @Attribute
    public String version;

    @Element
    Channel channel;


    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}