package com.tylersuehr.cleanarchitecture.ui.adapters;

import android.support.v7.widget.RecyclerView;

import com.tylersuehr.cleanarchitecture.R;

import java.util.LinkedList;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
abstract class PlaceholderAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private final List<T> items = new LinkedList<>();
    private RecyclerView delegate;


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setDelegate(RecyclerView delegate) {
        this.delegate = delegate;
    }

    public void add(T item) {
        this.items.add(item);
        this.notifyItemInserted(items.size());

        // Remove placeholder if available
        updatePlaceholder();
    }

    public void add(List<T> items) {
        int oldSize = items.size();
        this.items.addAll(items);
        this.notifyItemRangeInserted(oldSize, items.size());

        // Remove placeholder if available
        updatePlaceholder();
    }

    public void replace(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        this.notifyItemRangeChanged(0, items.size());
    }

    public void clear() {
        if (!items.isEmpty()) {
            int oldSize = items.size();
            this.items.clear();
            this.notifyItemRangeRemoved(0, oldSize);

            // Show placeholder if available
            updatePlaceholder();
        }
    }

    public T get(int position) {
        return items.get(position);
    }

    public List<T> getAll() {
        return items;
    }

    private void updatePlaceholder() {
        if (delegate == null) {
            return;
        }

        if (!items.isEmpty()) {
            this.delegate.setBackgroundResource(R.color.grey_100);
        } else {
            this.delegate.setBackgroundResource(R.drawable.placeholder);
        }
    }
}