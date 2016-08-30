package com.tylersuehr.cleanarchitecture.ui.adapters;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.ui.models.HelpHeader;
import com.tylersuehr.cleanarchitecture.ui.models.HelpItem;
import java.util.ArrayList;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/30/2016.
 */
public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.Holder> {
    private static final int HEADER = 0;
    private static final int CARD = 1;
    private List<Object> items = new ArrayList<>();


    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof HelpItem)
            return CARD;
        else if (items.get(position) instanceof HelpHeader)
            return HEADER;
        return -1;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        Holder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case CARD:
                v = inflater.inflate(R.layout.adapter_help, parent, false);
                holder = new Holder(v);
                holder.text1 = (TextView)v.findViewById(R.id.title);
                holder.text2 = (TextView)v.findViewById(R.id.description);
                holder.text2.setMovementMethod(LinkMovementMethod.getInstance());
                return holder;
            case HEADER:
                v = inflater.inflate(R.layout.adapter_dev, parent, false);
                holder = new Holder(v);
                holder.text1 = (TextView)v.findViewById(R.id.title);
                holder.text1.setMovementMethod(LinkMovementMethod.getInstance());
                return holder;
        }
        throw new RuntimeException("Invalid view holder!");
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == CARD) {
            HelpItem item = (HelpItem)items.get(position);
            holder.text1.setText(item.getTitle());
            holder.text2.setText(Html.fromHtml(item.getDescription()));
        } else if (viewType == HEADER) {
            HelpHeader item = (HelpHeader)items.get(position);
            holder.text1.setText(Html.fromHtml(item.getDescription()));
        }
    }

    public void add(Object o) {
        this.items.add(o);
        this.notifyItemInserted(getActualCount());
    }

    public void clear() {
        int count = getActualCount();
        this.items.clear();
        this.notifyItemRangeRemoved(0, count);
    }

    private int getActualCount() {
        return items.size() - 1;
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView text1, text2;


        public Holder(View v) {
            super(v);
        }
    }
}