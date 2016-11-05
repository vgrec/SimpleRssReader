package com.simplerssreader.http.di;

import com.simplerssreader.main.RssFragment;
import com.simplerssreader.main.RssListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RssListModule {

    private RssFragment rssFragment;

    public RssListModule(RssFragment rssFragment) {
        this.rssFragment = rssFragment;
    }

    @Provides
    RssFragment provideRssFragment() {
        return rssFragment;
    }

    @Provides
    RssListPresenter providesRssListPresenter(RssFragment view) {
        return new RssListPresenter(view);
    }
}
