package com.simplerssreader.http;

import android.util.Log;

import com.simplerssreader.model.Rss;
import com.simplerssreader.model.SimpleItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;

public class HttpRssLoader {

    public Observable<List<SimpleItem>> loadRss(final String url) {
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

    private List<SimpleItem> makeRequestAndParse(String url) throws IOException, XmlPullParserException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PcWorldRssService.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        PcWorldRssService service = retrofit.create(PcWorldRssService.class);
        Rss rss = service.getRssFeed().execute().body();

        return Observable.from(rss.getChannel().getItemList())
                .map(item -> new SimpleItem(item.getTitle(), item.getLink()))
                .toList()
                .toBlocking()
                .single();
    }

}
