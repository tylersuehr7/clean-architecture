package com.tylersuehr.cleanarchitecture.ui.adapters;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.Tablet;
import com.tylersuehr.cleanarchitecture.data.models.Technology;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.models.Watch;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class PlaceholderItemAdapter extends RecyclerView.Adapter<PlaceholderItemAdapter.Holder> {
    private static final int USER = 0;
    private static final int PHONE = 1;
    private static final int TABLET = 2;
    private static final int WATCH = 3;
    private RecyclerView delegate;
    private List<Object> items;


    public PlaceholderItemAdapter() {
        this.items = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof User)
            return USER;
        else if (items.get(position) instanceof Phone)
            return PHONE;
        else if (items.get(position) instanceof Tablet)
            return TABLET;
        else if (items.get(position) instanceof Watch)
            return WATCH;
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
            case USER:
                v = inflater.inflate(R.layout.adapter_chip, parent, false);
                holder = new Holder(v);
                holder.text1 = (TextView)v.findViewById(R.id.title);
                holder.image = (ImageView)v.findViewById(R.id.image);
                return holder;
            case PHONE:
            case TABLET:
            case WATCH:
                v = inflater.inflate(R.layout.adapter_card, parent, false);
                holder = new Holder(v);
                holder.text1 = (TextView)v.findViewById(R.id.title);
                holder.text2 = (TextView)v.findViewById(R.id.description);
                holder.image = (ImageView)v.findViewById(R.id.image);
                return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == USER) {
            User user = (User)items.get(position);
            String name = user.getFirstName();
            name += " " + user.getLastName();
            holder.text1.setText(name);
            holder.image.setImageResource(R.drawable.ic_person_24dp);
        } else {
            Technology tech = (Technology)items.get(position);
            holder.image.setImageResource(tech.getIcon(holder.image.getResources()));
            holder.text1.setText(tech.getClass().getSimpleName());
            holder.text2.setText(tech.getBrand() + " " + tech.getModel() + " $" + tech.getPrice());
        }
    }

    public void setDelegate(RecyclerView rv) {
        this.delegate = rv;
        this.delegate.setBackgroundResource(R.drawable.placeholder);
    }

    public void add(Object e) {
        this.items.add(e);
        this.notifyItemInserted(getActualCount());

        // Remove the placeholder
        if (delegate != null) {
            this.delegate.setBackgroundResource(R.color.grey_100);
        }
    }

    public void addAll(Collection<Object> objects) {
        this.items = (List<Object>)objects;
        this.notifyItemRangeInserted(0, getActualCount());

        // Remove the placeholder
        if (delegate != null && objects.size() > 0) {
            this.delegate.setBackgroundResource(R.color.grey_100);
        }
    }

    public void clear() {
        int count = getActualCount();
        this.items.clear();
        this.notifyItemRangeRemoved(0, count);

        // Show the placeholder
        if (delegate != null) {
            this.delegate.setBackgroundResource(R.drawable.placeholder);
        }
    }

    private int getActualCount() {
        return items.size() - 1;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ImageView image;


        public Holder(View v) {
            super(v);
        }
    }
}