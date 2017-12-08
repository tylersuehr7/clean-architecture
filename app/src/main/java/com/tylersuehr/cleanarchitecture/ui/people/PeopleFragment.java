package com.tylersuehr.cleanarchitecture.ui.people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.ui.AbstractBaseFragment;
import com.tylersuehr.cleanarchitecture.ui.Navigator;
import com.tylersuehr.cleanarchitecture.ui.about.AboutActivity;
import com.tylersuehr.cleanarchitecture.ui.shared.AlertUtils;
import com.tylersuehr.cleanarchitecture.ui.views.EmptyStateRecyclerView;

import java.util.List;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public class PeopleFragment extends AbstractBaseFragment implements PeopleContract.IPeopleView {
    private PeopleContract.IPeoplePresenter presenter;

    private EmptyStateRecyclerView recycler;
    private PersonItemAdapter adapter;


    public static PeopleFragment create(PeopleContract.IPeoplePresenter presenter) {
        PeopleFragment frag = new PeopleFragment();
        frag.presenter = presenter;
        frag.presenter.attach(frag);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_people, container, false);

        // Setup recycler
        this.adapter = new PersonItemAdapter();
        this.recycler = v.findViewById(R.id.recycler);
        this.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recycler.setAutoEmptyState(false);
        this.recycler.setAdapter(adapter);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.presenter.loadPeople();
    }

    /**
     * Detaching view will prevent memory leak!
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.presenter.detach();
        this.presenter.stopTasks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                PersonBottomSheet.show(getFragmentManager(), new PersonBottomSheet.PersonDialogListener() {
                    @Override
                    public void onPersonAdded(String first, String last, int age) {
                        presenter.createPerson(first, last, age);
                    }
                });
                break;
            case R.id.action_clear:
                AlertUtils.confirm(getActivity(), "Clear all people?", "YES", new Runnable() {
                    @Override
                    public void run() {
                        adapter.clearPeople();
                        presenter.deleteAllPeople();
                    }
                });
                break;
            case R.id.action_about:
                Navigator.from(getActivity()).to(AboutActivity.class).go();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPeopleReady(List<Person> people) {
        this.adapter.clearPeople();
        this.adapter.addPeople(people);
        this.recycler.setShowEmptyState(false);
    }

    @Override
    public void onPeopleUnavailable() {
        this.recycler.setShowEmptyState(true);
    }

    @Override
    public void onPersonCreated(Person createdPerson) {
        this.adapter.addPerson(createdPerson);
        this.recycler.setShowEmptyState(false);
    }

    @Override
    public void onPeopleDeleted() {
        this.adapter.clearPeople();
        this.recycler.setShowEmptyState(true);
    }
}