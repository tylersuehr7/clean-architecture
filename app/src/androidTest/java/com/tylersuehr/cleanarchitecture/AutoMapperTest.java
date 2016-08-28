package com.tylersuehr.cleanarchitecture;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import com.tylersuehr.cleanarchitecture.data.mappers.AutoMapper;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.models.Watch;
import java.util.ArrayList;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class AutoMapperTest extends ApplicationTestCase<Application> {
    public AutoMapperTest() {
        super(Application.class);
    }

    @SmallTest
    public void testUserAutoMap() {
        AutoMapper<User> mapper = new AutoMapper<>(User.class);
        ContentValues values = mapper.toContentValues(getUser());
        assertNotNull(values);
    }

    @SmallTest
    public void testWatchAutoMap() {
        AutoMapper<Watch> mapper = new AutoMapper<>(Watch.class);
        ContentValues values = mapper.toContentValues(getWatch());
        assertNotNull(values);
    }

    @SmallTest
    public void testUserCursor() {
        AutoMapper<User> mapper = new AutoMapper<>(User.class);
        Cursor cursor = new TestUserCursor("Tyler,Kristian,Jack,John,Tom,Jake");
        cursor.moveToFirst();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            users.add(mapper.map(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        assertNotNull(users);
    }

    @SmallTest
    public void testWatchCursor() {
        AutoMapper<Watch> mapper = new AutoMapper<>(Watch.class);
        Cursor c = new TestWatchCursor("Apple,Google,Microsoft,Hauwei,Samsung,LG");
        c.moveToFirst();

        List<Watch> watches = new ArrayList<>();
        for (int i = 0; i < c.getCount(); i++) {
            watches.add(mapper.map(c));
            c.moveToNext();
        }

        c.close();
        assertNotNull(watches);
    }

    private Watch getWatch() {
        Watch watch = new Watch();
        watch.setBrand("Hauwei");
        watch.setModel("W360");
        watch.setPrice(299.99);
        watch.setIconResName(null);
        watch.setCircular(true);
        return watch;
    }

    private User getUser() {
        return new User("Tyler", "Suehr", "tylersuehr@gmail.com");
    }
}