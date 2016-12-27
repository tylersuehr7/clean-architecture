package com.tylersuehr.cleanarchitecture.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.User;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class ItemAdapter extends PlaceholderAdapter<Entity, ItemAdapter.Holder> {
    private static final int USER = 0;
    private static final int PHONE = 1;
    private static final int CAR = 2;


    @Override
    public int getItemViewType(int position) {
        if (get(position) instanceof User)
            return USER;
        else if (get(position) instanceof Phone)
            return PHONE;
        else if (get(position) instanceof Car)
            return CAR;
        return -1;
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
            case CAR:
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
            User user = (User)get(position);
            String name = user.getFirstName();
            name += " " + user.getLastName();
            holder.text1.setText(name);
            holder.image.setImageResource(R.drawable.ic_person_24dp);
        } else if (viewType == PHONE) {
            Phone phone = (Phone)get(position);
            holder.image.setImageResource(R.drawable.ic_smartphone_24dp);
            holder.text1.setText(phone.getModel());
            holder.text2.setText(phone.getMake() + " $" + phone.getPrice());
        } else if (viewType == CAR) {
            Car car = (Car)get(position);
            holder.image.setImageResource(R.drawable.ic_drive_24dp);
            holder.text1.setText(car.getMake() + " " + car.getModel());
            holder.text2.setText(car.getColor() + " " + car.getYear());
        }
    }


    static class Holder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ImageView image;


        Holder(View v) {
            super(v);
        }
    }
}