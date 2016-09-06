package com.simplerssreader.main;

import com.simplerssreader.RssItem;

import java.util.List;

/**
 * Specifies the contract between the view and the presenter.
 */
public interface RssListContract {

    interface View {
        void showItems(List<RssItem> items);

        void showLoadingIndicator(boolean show);

        void openInWebView(String url);
    }

    interface Presenter {

        void loadItems();

        void viewArticleDetail(RssItem item);

        void cancel();
    }
}
