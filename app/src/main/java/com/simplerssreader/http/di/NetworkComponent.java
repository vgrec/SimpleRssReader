package com.simplerssreader.http.di;

import com.simplerssreader.main.RssListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {
    void inject(RssListPresenter presenter);
}
