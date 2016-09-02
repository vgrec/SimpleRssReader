package com.simplerssreader.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.simplerssreader.HttpRssLoader;
import com.simplerssreader.RssItem;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link RssFragment}), retrieves the data and updates the
 * UI as required.
 */
public class PresenterImpl implements RssListContract.Presenter {
    private static final String RSS_LINK = "http://www.pcworld.com/index.rss";

    private RssListContract.View view;
    private HttpRssLoader rssLoader;
    private Subscription subscription;

    public PresenterImpl(RssListContract.View view, HttpRssLoader rssLoader) {
        this.view = view;
        this.rssLoader = rssLoader;
    }

    @Override
    public void loadItems() {
        view.showLoadingIndicator(true);
        subscription = rssLoader.loadRss(RSS_LINK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    view.showLoadingIndicator(false);
                    view.showItems(result);
                });
    }

    @Override
    public void openItemInWebView(Context context, RssItem item) {
        Uri uri = Uri.parse(item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public void cancel() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
