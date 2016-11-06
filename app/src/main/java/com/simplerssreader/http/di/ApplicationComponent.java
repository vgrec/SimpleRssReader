package com.simplerssreader.http.di;

import com.simplerssreader.main.RssFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                RssListModule.class
        })
public interface ApplicationComponent {
    void inject(RssFragment target);
}
