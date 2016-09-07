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
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(makeRequestAndParse(url));
                subscriber.onCompleted();
            } catch (XmlPullParserException | IOException e) {
                Log.w(e.getMessage(), e);
                subscriber.onError(e);
            }
        });
    }

    private List<RssItem> makeRequestAndParse(String url) throws IOException, XmlPullParserException {
        PcWorldRssParser parser = new PcWorldRssParser();
        return parser.parse(getInputStream(url));
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
