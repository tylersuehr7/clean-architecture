package com.tylersuehr.cleanarchitecture.ui.people;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.ui.BaseAdapter;
import com.tylersuehr.cleanarchitecture.ui.views.CircleImageView;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 */
class PersonAdapter extends BaseAdapter<Person, PersonAdapter.Holder> {
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_person_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Person person = get(position);
        holder.title.setText(person.getFirstName() + " "+ person.getLastName());
        holder.age.setText(TextUtils.concat("Age ", String.valueOf(person.getAge())));

        final Resources res = holder.image.getResources();
        Picasso.with(holder.image.getContext())
                .load(res.getIdentifier(person.getImage(), "drawable", "com.tylersuehr.cleanarchitecture"))
                .into(holder.image);
    }

    static class Holder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView title, age;

        Holder(View v) {
            super(v);
            this.title = (TextView)v.findViewById(R.id.title);
            this.age = (TextView)v.findViewById(R.id.age);
            this.image = (CircleImageView)v.findViewById(R.id.image);
        }
    }
}