package com.simplerssreader.http;


import com.simplerssreader.model.Rss;

import retrofit2.http.GET;
import rx.Observable;

public interface PcWorldRssService {
    String BASE_URL = "http://pcworld.com";

    @GET("index.rss")
    Observable<Rss> getRssFeed();
}
