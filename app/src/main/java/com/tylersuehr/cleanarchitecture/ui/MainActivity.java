package com.tylersuehr.cleanarchitecture.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.tylersuehr.cleanarchitecture.tasks.FindAllTask;
import com.tylersuehr.cleanarchitecture.tasks.ITask;
import com.tylersuehr.cleanarchitecture.tasks.TaskExecutor;
import com.tylersuehr.cleanarchitecture.ui.adapters.PlaceholderItemAdapter;
import com.tylersuehr.cleanarchitecture.ui.utils.SlideInItemAnimator;
import com.tylersuehr.cleanarchitecture.ui.utils.TestingUtils;
import com.tylersuehr.cleanarchitecture.ui.views.CardSpacer;
import java.util.Collection;

public class MainActivity extends BaseActivity implements ITask {
    private PlaceholderItemAdapter adapter;
    private RepositoryManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup recycler
        adapter = new PlaceholderItemAdapter();
        RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setItemAnimator(new SlideInItemAnimator());
        recycler.addItemDecoration(new CardSpacer());
        recycler.setAdapter(adapter);
        adapter.setDelegate(recycler);
        manager = RepositoryManager.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Register our activity for task callbacks
        TaskExecutor.register(this);

        // Load all data from our database
        TaskExecutor.execute(new FindAllTask(manager, this));
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister our activity for task callbacks
        TaskExecutor.unregister(this);
    }

    @Override
    public void onTaskCompleted(Collection<Object> objects) {
        // Add all the items loaded into our recycler adapter
        adapter.addAll(objects);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                // Add a test user
                User user = TestingUtils.createTestUser();
                adapter.add(user);
                manager.getUsers().add(user);
                break;
            case R.id.action_phone:
                // Add a test phone
                Phone phone = TestingUtils.createTestPhone(this);
                adapter.add(phone);
                manager.getPhones().add(phone);
                break;
            case R.id.action_tablet:
                // Add a test tablet
                Tablet tablet = TestingUtils.createTestTablet(this);
                adapter.add(tablet);
                manager.getTablets().add(tablet);
                break;
            case R.id.action_watch:
                // Add a test watch
                Watch watch = TestingUtils.createTestWatch(this);
                adapter.add(watch);
                manager.getWatches().add(watch);
                break;
            case R.id.action_clear:
                // Ask to remove items. If confirmed, remove all items in database
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
                // Open the help activity
                ActivityOptionsCompat op = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                startActivity(new Intent(this, HelpActivity.class), op.toBundle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}