package com.simplerssreader.http.di;

import com.simplerssreader.http.PcWorldRssService;
import com.simplerssreader.main.RssListContract;
import com.simplerssreader.main.RssListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RssListModule {

    private RssListContract.View view;

    public RssListModule(RssListContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    RssListContract.View provideRssView() {
        return view;
    }

    @Provides
    @Singleton
    RssListPresenter providesRssListPresenter(RssListContract.View view, PcWorldRssService rssService) {
        return new RssListPresenter(view, rssService);
    }
}
