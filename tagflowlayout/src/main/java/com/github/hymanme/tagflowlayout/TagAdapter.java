package com.github.hymanme.tagflowlayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.hymanme.tagflowlayout.bean.TagBean;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * Author   :hyman
 * Email    :hymanme@163.com
 * Create at 2016/9/4
 * Description:
 */
public abstract class TagAdapter<T extends TagBean> extends BaseAdapter {
    private final List<T> mTagBeans;

    public TagAdapter() {
        this.mTagBeans = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mTagBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mTagBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public List<T> getTagBeans() {
        return mTagBeans;
    }

    public void addTag(T tag) {
        mTagBeans.add(tag);
        notifyDataSetChanged();
    }

    public void addAllTags(List<T> tags) {
        mTagBeans.clear();
        mTagBeans.addAll(tags);
        notifyDataSetChanged();
    }

    public void select(int position) {
        if (position < 0 || position >= mTagBeans.size()) {
            return;
        }
        mTagBeans.get(position).setSelected(true);
    }

    public void unSelect(int position) {
        if (position < 0 || position >= mTagBeans.size()) {
            return;
        }
        mTagBeans.get(position).setSelected(false);
    }
}
