package com.simplerssreader.http.di;

import com.simplerssreader.main.RssFragment;
import com.simplerssreader.main.RssListContract;

import dagger.Component;

/**
 * author vgrec-home on 05.11.16.
 */
@Component(modules = RssListModule.class)
public interface RssListComponent {
    void inject(RssFragment target);

    void inject(RssListContract.View target);
}
