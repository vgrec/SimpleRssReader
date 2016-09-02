package com.simplerssreader.main;

import android.content.Context;

import com.simplerssreader.RssItem;

import java.util.List;

/**
 * Specifies the contract between the view and the presenter.
 */
public interface RssListContract {

    interface View {
        void showItems(List<RssItem> items);

        void showLoadingIndicator(boolean show);
    }

    interface Presenter {

        void loadItems();

        void openItemInWebView(Context context, RssItem item);

        void cancel();
    }
}
