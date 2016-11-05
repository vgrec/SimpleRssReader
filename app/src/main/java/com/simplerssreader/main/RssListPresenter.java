package com.simplerssreader.main;

import com.simplerssreader.http.PcWorldRssService;
import com.simplerssreader.http.di.DaggerNetworkComponent;
import com.simplerssreader.http.di.NetworkModule;
import com.simplerssreader.model.Item;
import com.simplerssreader.model.SimpleItem;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
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

    @Inject
    PcWorldRssService rssService;

    public RssListPresenter(RssListContract.View view) {
        this.view = view;
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule())
                .build()
                .inject(this);
    }

    @Override
    public void loadItems() {
        subscription = rssService.getRssFeed()
                .map(rss -> convertFromSource(rss.getChannel().getItemList()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoadingIndicator(true))
                .doOnTerminate(() -> view.showLoadingIndicator(false))
                .subscribe(
                        result -> view.showItems(result),
                        error -> view.showError()
                );
    }

    private List<SimpleItem> convertFromSource(List<Item> items) {
        return Observable.from(items)
                .map(item -> new SimpleItem(item.getTitle(), item.getLink()))
                .toList()
                .toBlocking()
                .single();
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
