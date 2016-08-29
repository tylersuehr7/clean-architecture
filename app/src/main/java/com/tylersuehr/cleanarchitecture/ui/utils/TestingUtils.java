package com.tylersuehr.cleanarchitecture.ui.utils;
import android.content.Context;
import android.content.res.Resources;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.Tablet;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.models.Watch;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public final class TestingUtils {
    public static User createTestUser() {
        return new User("Tyler", "Suehr", "tylersuehr@gmail.com");
    }

    public static Phone createTestPhone(Context c) {
        Resources res = c.getResources();
        Phone phone = new Phone();
        phone.setBrand("Huawei");
        phone.setModel("Nexus 6P");
        phone.setPrice(499.99);
        phone.setIconResName(res.getResourceName(R.drawable.ic_smartphone_24dp));
        phone.addEligibleProvider("Sprint")
                .addEligibleProvider("Verizon")
                .addEligibleProvider("AT&T")
                .addEligibleProvider("T-Mobile");
        return phone;
    }

    public static Tablet createTestTablet(Context c) {
        Resources res = c.getResources();
        Tablet tablet = new Tablet();
        tablet.setBrand("HTC");
        tablet.setModel("Nexus 9");
        tablet.setPrice(299.99);
        tablet.setIconResName(res.getResourceName(R.drawable.ic_tablet_24dp));
        tablet.setSize(Tablet.LARGE);
        return tablet;
    }

    public static Watch createTestWatch(Context c) {
        Resources res = c.getResources();
        Watch watch = new Watch();
        watch.setBrand("Huawei");
        watch.setModel("Wear");
        watch.setPrice(299.99);
        watch.setIconResName(res.getResourceName(R.drawable.ic_watch_24dp));
        watch.setCircular(false);
        return watch;
    }
}