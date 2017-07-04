package com.tylersuehr.cleanarchitecture.ui;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Just a simple RecyclerView adapter that manages a list of items.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private final List<T> items;


    public BaseAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(T item) {
        this.items.add(item);
        this.notifyItemInserted(items.size());
    }

    public void addAll(Collection<T> items) {
        int count = items.size();
        this.items.addAll(items);
        this.notifyItemRangeInserted(count, items.size());
    }

    public void remove(T item) {
        int index = items.indexOf(item);
        this.items.remove(item);
        this.notifyItemRemoved(index);
    }

    public void remove(int index) {
        this.items.remove(index);
        this.notifyItemRemoved(index);
    }

    public void clear() {
        if (!items.isEmpty()) {
            int count = items.size();
            this.items.clear();
            this.notifyItemRangeRemoved(0, count);
        }
    }

    public T get(int position) {
        return items.get(position);
    }

    public List<T> getAll() {
        return items;
    }
}