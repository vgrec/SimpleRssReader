package com.simplerssreader.http;


import com.simplerssreader.model.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PcWorldRssService {
    String BASE_URL = "http://pcworld.com";

    @GET("index.rss")
    Call<Rss> getRssFeed();
}
