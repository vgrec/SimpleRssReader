package com.simplerssreader;

import com.simplerssreader.main.PresenterImpl;
import com.simplerssreader.main.RssListContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link com.simplerssreader.main.PresenterImpl} logic.
 * <p>
 * Created by vgrec on 06.09.16.
 */
public class PresenterTest {

    private static final String LINK = "http://google.com";
    private RssListContract.View mockView;
    private Observable<List<RssItem>> mockObservable;

    private RssListContract.Presenter presenter;
    private HttpRssLoader mockLoader;
    private List<RssItem> rssItems = new ArrayList<>();
    private RssItem item;

    @Before
    public void setUp() {
        Utils.setupRxJava();

        mockView = mock(RssListContract.View.class);
        mockLoader = mock(HttpRssLoader.class);
        presenter = new PresenterImpl(mockView, mockLoader);

        // setup test data
        item = new RssItem("Mock Item", LINK);
        rssItems.add(item);
    }

    @Test
    public void shouldOpenWebView() {
        presenter.viewArticleDetail(item);
        verify(mockView).openInWebView(LINK);
    }


    @Test
    public void shouldLoadRssItem() {
        mockObservable = Observable.just(rssItems);
        when(mockLoader.loadRss(anyString())).thenReturn(mockObservable);

        presenter.loadItems();
        verify(mockView, times(1)).showLoadingIndicator(true);
        verify(mockView, times(1)).showLoadingIndicator(false);
        verify(mockView).showItems(anyListOf(RssItem.class));
    }

    @Test
    public void shouldShowError() {
        mockObservable = Observable.error(new IOException());
        when(mockLoader.loadRss(anyString())).thenReturn(mockObservable);

        presenter.loadItems();
        verify(mockView, times(0)).showItems(anyListOf(RssItem.class));
        verify(mockView).showLoadingIndicator(false);
        verify(mockView).showError();
    }

    @After
    public void tearDown() {
        Utils.resetRxJava();
    }

}
