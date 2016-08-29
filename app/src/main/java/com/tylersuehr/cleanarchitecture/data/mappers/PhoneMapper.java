package com.tylersuehr.cleanarchitecture.data.mappers;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class PhoneMapper extends TechnologyMapper<Phone> {
    @Override
    public Phone map(Cursor c) {
        Phone phone = new Phone();
        phone.setTechnology(mapTechnology(c));
        String[] providers = c.getString(c.getColumnIndex("providers")).split(",");
        for (String p : providers) {
            phone.addEligibleProvider(p);
        }
        return phone;
    }
}