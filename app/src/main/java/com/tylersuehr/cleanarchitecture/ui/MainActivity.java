package com.tylersuehr.cleanarchitecture.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.Tablet;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.models.Watch;
import com.tylersuehr.cleanarchitecture.data.repository.RepositoryManager;
import com.tylersuehr.cleanarchitecture.ui.adapters.ItemAdapter;
import com.tylersuehr.cleanarchitecture.ui.utils.TestingUtils;
import com.tylersuehr.cleanarchitecture.ui.views.CardSpacer;

public class MainActivity extends AppCompatActivity {
    private RepositoryManager manager;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup recycler
        adapter = new ItemAdapter();
        RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new CardSpacer());

        // Show all local data
        manager = RepositoryManager.getInstance(this);
        for (User user : manager.getUsers().find(null, null, null))
            adapter.add(user);
        for (Phone phone : manager.getPhones().find(null, null, null))
            adapter.add(phone);
        for (Tablet tablet : manager.getTablets().find(null, null, null))
            adapter.add(tablet);
        for (Watch watch : manager.getWatches().find(null, null, null))
            adapter.add(watch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                User user = TestingUtils.createTestUser();
                adapter.add(user);
                manager.getUsers().add(user);
                break;
            case R.id.action_phone:
                Phone phone = TestingUtils.createTestPhone(this);
                adapter.add(phone);
                manager.getPhones().add(phone);
                break;
            case R.id.action_tablet:
                Tablet tablet = TestingUtils.createTestTablet(this);
                adapter.add(tablet);
                manager.getTablets().add(tablet);
                break;
            case R.id.action_watch:
                Watch watch = TestingUtils.createTestWatch(this);
                adapter.add(watch);
                manager.getWatches().add(watch);
                break;
            case R.id.action_clear:
                // TODO: Remove all database items
                break;
            case R.id.action_about:
                // TODO: Open about activity
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}