package com.tylersuehr.cleanarchitecture.ui;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.tylersuehr.cleanarchitecture.R;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected Snackbar shortSnack(String text) {
        View v = findViewById(R.id.root);
        return Snackbar.make(v, text, Snackbar.LENGTH_SHORT);
    }

    protected Snackbar longSnack(String text) {
        View v = findViewById(R.id.root);
        return Snackbar.make(v, text, Snackbar.LENGTH_LONG);
    }
}