package com.simplerssreader.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Author vgrec, on 09.07.16.
 */
@Root(name = "item", strict = false)
public class Item implements Parcelable {

    @Element(name = "title", required = true)
    private String title;


    @Element(name = "link", required = true)
    private String link;

    @Element(name = "description", required = true)
    private String description;


    @Element(name = "guid", required = false)
    private String guid; // A string that uniquely identifies the item.

    @Element(name = "pubDate", required = false)
    private String pubDate;

    @Namespace(prefix = "content")
    @Element(name = "encoded", required = false)
    private String content;

    @Namespace(prefix = "creator")
    @Element(name = "creator", required = false)
    private String dc;

    public Item() {
    }

    public Item(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getLink() {
        return link;
    }

    public String getGuid() {
        return guid;
    }

    public String getContent() {
        return content;
    }

    public String getCreator() {
        return dc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.description);
        dest.writeString(this.guid);
        dest.writeString(this.pubDate);
        dest.writeString(this.content);
        dest.writeString(this.dc);
    }

    protected Item(Parcel in) {
        this.title = in.readString();
        this.link = in.readString();
        this.description = in.readString();
        this.guid = in.readString();
        this.pubDate = in.readString();
        this.content = in.readString();
        this.dc = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
