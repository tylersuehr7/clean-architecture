package com.tylersuehr.cleanarchitecture.ui;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.Tablet;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.models.Watch;
import com.tylersuehr.cleanarchitecture.data.repository.RepositoryManager;
import com.tylersuehr.cleanarchitecture.tasks.FindTask;
import com.tylersuehr.cleanarchitecture.tasks.ITask;
import com.tylersuehr.cleanarchitecture.ui.adapters.ItemAdapter;
import com.tylersuehr.cleanarchitecture.ui.utils.TestingUtils;
import com.tylersuehr.cleanarchitecture.ui.views.CardSpacer;
import java.util.Collection;

public class MainActivity extends BaseActivity implements ITask {
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
        new FindTask(manager, this).setCallback(this).execute();
    }

    @Override
    public void onTaskCompleted(Collection<Object> objects) {
        adapter.addAll(objects);
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
                Snackbar snack = longSnack("Are you sure you want to clear all items?");
                snack.setAction("Delete", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        manager.getUsers().remove(null);
                        manager.getPhones().remove(null);
                        manager.getTablets().remove(null);
                        manager.getWatches().remove(null);
                        adapter.clear();
                        shortSnack("Items cleared!").show();
                    }
                });
                snack.show();
                break;
            case R.id.action_about:
                // TODO: Open about activity
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}