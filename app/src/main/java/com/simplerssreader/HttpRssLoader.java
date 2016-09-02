package com.simplerssreader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import rx.Observable;

public class HttpRssLoader {

    public Observable<List<RssItem>> loadRss(final String url) {
        return Observable.fromCallable(() -> makeRequest(url));
    }

    private List<RssItem> makeRequest(String url) {
        List<RssItem> rssItems = null;
        try {
            PcWorldRssParser parser = new PcWorldRssParser();
            rssItems = parser.parse(getInputStream(url));
        } catch (XmlPullParserException | IOException e) {
            Log.w(e.getMessage(), e);
        }
        return rssItems;
    }

    private InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(Constants.TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}
