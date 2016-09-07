package com.simplerssreader.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simplerssreader.HttpRssLoader;
import com.simplerssreader.R;
import com.simplerssreader.RssAdapter;
import com.simplerssreader.RssItem;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

public class RssFragment extends Fragment implements OnItemClickListener, RssListContract.View {

    private ProgressBar progressBar;
    private ListView listView;
    private RssListContract.Presenter presenter;

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_layout, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new PresenterImpl(this, new HttpRssLoader());
        presenter.loadItems();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);
        presenter.viewArticleDetail(item);
    }

    @Override
    public void showItems(List<RssItem> items) {
        if (items != null) {
            RssAdapter adapter = new RssAdapter(getActivity(), items);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(),
                    "An error occurred while downloading the rss feed.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        progressBar.setVisibility(show ? android.view.View.VISIBLE : android.view.View.GONE);
    }

    @Override
    public void openInWebView(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void showError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancel();
    }
}
