package com.simplerssreader.main;

import com.simplerssreader.model.Item;

import java.util.List;

/**
 * Specifies the contract between the view and the presenter.
 */
public interface RssListContract {

    interface View {
        void showItems(List<Item> items);

        void showLoadingIndicator(boolean show);

        void openInWebView(String url);

        void showError();
    }

    interface Presenter {

        void loadItems();

        void viewArticleDetail(Item item);

        void cancel();
    }
}
