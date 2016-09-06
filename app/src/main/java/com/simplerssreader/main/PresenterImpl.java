package com.simplerssreader.main;

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
    public void viewArticleDetail(RssItem item) {
        view.openInWebView(item.getLink());
    }

    @Override
    public void cancel() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
