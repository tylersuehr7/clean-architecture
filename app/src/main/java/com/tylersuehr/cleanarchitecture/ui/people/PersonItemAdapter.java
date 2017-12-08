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
import com.tylersuehr.cleanarchitecture.ui.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Tyler Suehr
 *
 * @author Tyler Suehr
 * @version 1.0
 */
class PersonItemAdapter extends RecyclerView.Adapter<PersonItemAdapter.Holder> {
    /* Stores the list of items to adapt */
    private final List<Person> items = new ArrayList<>();


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_person_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Person person = getPersonAt(position);
        holder.title.setText(person.getFirstName() + " "+ person.getLastName());
        holder.age.setText(TextUtils.concat("Age ", String.valueOf(person.getAge())));

        final Resources res = holder.image.getResources();
        final int pic = res.getIdentifier(person.getImage(), "drawable",
                "com.tylersuehr.cleanarchitecture");

        Picasso.with(holder.image.getContext())
                .load(pic).into(holder.image);
    }

    void addPeople(List<Person> people) {
        final int size = items.size();
        this.items.addAll(people);
        notifyItemRangeInserted(size, items.size());
    }

    void addPerson(Person person) {
        this.items.add(person);
        notifyItemInserted(items.size());
    }

    void clearPeople() {
        if (items.size() > 0) {
            final int size = items.size();
            this.items.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    List<Person> getPeople() {
        return items;
    }

    Person getPersonAt(int position) {
        return items.get(position);
    }


    static class Holder extends RecyclerView.ViewHolder {
        final CircleImageView image;
        final TextView title, age;

        Holder(final View v) {
            super(v);
            this.title = v.findViewById(R.id.title);
            this.age = v.findViewById(R.id.age);
            this.image = v.findViewById(R.id.image);
        }
    }
}