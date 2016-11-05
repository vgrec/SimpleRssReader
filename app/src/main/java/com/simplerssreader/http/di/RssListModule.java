package com.simplerssreader.http.di;

import com.simplerssreader.main.RssListContract;
import com.simplerssreader.main.RssListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RssListModule {

    private RssListContract.View view;

    public RssListModule(RssListContract.View view) {
        this.view = view;
    }

    @Provides
    RssListContract.View provideRssFragment() {
        return view;
    }

    @Provides
    RssListPresenter providesRssListPresenter(RssListContract.View view) {
        return new RssListPresenter(view);
    }
}
