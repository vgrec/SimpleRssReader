package com.simplerssreader;

import com.simplerssreader.http.PcWorldRssService;
import com.simplerssreader.main.RssListPresenter;
import com.simplerssreader.main.RssListContract;
import com.simplerssreader.model.Channel;
import com.simplerssreader.model.Item;
import com.simplerssreader.model.Rss;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import rx.Observable;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the {@link RssListPresenter} logic.
 * <p>
 * Created by vgrec on 06.09.16.
 */
public class PresenterTest {

    private static final String LINK = "http://google.com";
    private RssListContract.View mockView;
    private Observable<Rss> mockObservable;

    private RssListContract.Presenter presenter;
    private Rss rss = new Rss();
    private Item item;
    private PcWorldRssService service;

    @Before
    public void setUp() {
        Utils.setupRxJava();

        service = mock(PcWorldRssService.class);
        mockView = mock(RssListContract.View.class);
        presenter = new RssListPresenter(mockView, service);

        // setup test data
        item = new Item("Mock Item", LINK);
        Channel channel = new Channel();
        channel.setItemList(Arrays.asList(item));
        rss.setChannel(channel);
    }

    @Test
    public void shouldOpenWebView() {
        presenter.viewArticleDetail(item);
        verify(mockView).openInWebView(LINK);
    }


    @Test
    public void shouldLoadRssItem() {
        mockObservable = Observable.just(rss);
        when(service.getRssFeed()).thenReturn(mockObservable);
        presenter.loadItems();
        verify(mockView, times(1)).showLoadingIndicator(true);
        verify(mockView, times(1)).showLoadingIndicator(false);
        verify(mockView).showItems(anyListOf(Item.class));
    }

    @Test
    public void shouldShowError() {
        mockObservable = Observable.error(new IOException());
        when(service.getRssFeed()).thenReturn(mockObservable);
        presenter.loadItems();
        verify(mockView, never()).showItems(anyListOf(Item.class));
        verify(mockView).showLoadingIndicator(false);
        verify(mockView).showError();
    }

    @After
    public void tearDown() {
        Utils.resetRxJava();
    }

}
