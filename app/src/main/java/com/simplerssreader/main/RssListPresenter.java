package com.simplerssreader.main;

import com.simplerssreader.http.PcWorldRssService;
import com.simplerssreader.model.Item;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link RssFragment}), retrieves the data and updates the
 * UI as required.
 */
public class RssListPresenter implements RssListContract.Presenter {
    private RssListContract.View view;
    private Subscription subscription;

    private PcWorldRssService rssService;

    @Inject
    public RssListPresenter(RssListContract.View view, PcWorldRssService rssService) {
        this.view = view;
        this.rssService = rssService;
    }

    @Override
    public void loadItems() {
        subscription = rssService.getRssFeed()
                .map(rss -> rss.getChannel().getItemList())
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
    public void viewArticleDetail(Item item) {
        view.openInWebView(item.getLink());
    }

    @Override
    public void cancel() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
