package com.simplerssreader.main;

import com.simplerssreader.http.HttpRssLoader;
import com.simplerssreader.model.SimpleItem;

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
        subscription = rssLoader.loadRss(RSS_LINK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoadingIndicator(true))
                .doOnTerminate(() -> view.showLoadingIndicator(false))
                .subscribe(
                        result -> view.showItems(result),
                        error -> view.showError()
                );
    }

    @Override
    public void viewArticleDetail(SimpleItem item) {
        view.openInWebView(item.getLink());
    }

    @Override
    public void cancel() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
