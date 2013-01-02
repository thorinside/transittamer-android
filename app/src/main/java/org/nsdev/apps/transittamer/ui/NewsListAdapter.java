package org.nsdev.apps.transittamer.ui;

import android.view.LayoutInflater;

import org.nsdev.apps.transittamer.R;
import org.nsdev.apps.transittamer.core.News;
import org.nsdev.apps.transittamer.ui.AlternatingColorListAdapter;

import java.util.List;

public class NewsListAdapter extends AlternatingColorListAdapter<News> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public NewsListAdapter(LayoutInflater inflater, List<News> items,
                               boolean selectable) {
        super(R.layout.news_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public NewsListAdapter(LayoutInflater inflater, List<News> items) {
        super(R.layout.news_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_title, R.id.tv_summary,
                R.id.tv_date };
    }

    @Override
    protected void update(int position, News item) {
        super.update(position, item);

        setText(R.id.tv_title, item.getTitle());
        setText(R.id.tv_summary, item.getContent());
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}