package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class PhoneMapper extends TechnologyMapper<Phone> {
    @Override
    public Phone map(Cursor c) {
        Phone phone = (Phone)mapTechnology(c);
        String[] providers = c.getString(c.getColumnIndex("providers")).split(",");
        for (String p : providers) {
            phone.addEligibleProvider(p);
        }
        return phone;
    }

    @Override
    public ContentValues toContentValues(Phone o) {
        ContentValues values = super.toContentValues(o);
        values.put("providers", o.getEligibleProviders().toString()
                .replace("[", "").replace("]", ""));
        return values;
    }
}