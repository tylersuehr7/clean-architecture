package com.tylersuehr.cleanarchitecture.ui.about;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.ui.shared.SpanUtils;
import com.tylersuehr.cleanarchitecture.ui.views.ElasticFrameLayout;

/**
 * Copyright 2017 Tyler Suehr
 *
 * This activity just allows the user to see simple 'about' information to explain what this
 * example application is for.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Check if we should use the API 21+ features or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Setup the activity's transitions
            TransitionInflater inflater = TransitionInflater.from(this);
            getWindow().setEnterTransition(inflater.inflateTransition(R.transition.about_enter));
            getWindow().setReturnTransition(inflater.inflateTransition(R.transition.about_return));

            // Finish the activity when dragged all the way up or down
            final ElasticFrameLayout dragFrame = (ElasticFrameLayout)findViewById(R.id.drag_container);
            dragFrame.addListener(new ElasticFrameLayout.SystemChromeFader(this) {
                @Override
                @TargetApi(21)
                public void onDragDismissed() {
                    // If we drag dismiss downward then the default reversal of the enter
                    // transition would slide content upward which looks weird. So reverse it.
                    if (dragFrame.getTranslationY() > 0) {
                        getWindow().setReturnTransition(TransitionInflater.from(AboutActivity.this)
                                .inflateTransition(R.transition.about_return_down));
                    }
                    finishAfterTransition();
                }
            });
        } else {
            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        // Setup the widgets used by both API 21- and API 21+ devices
        TextView summaryView = (TextView)findViewById(R.id.summary);
        summaryView.setMovementMethod(LinkMovementMethod.getInstance());
        Spanned aboutHtml = Html.fromHtml(getString(R.string.about));
        Spanned aboutDev = SpanUtils.center(Html.fromHtml(getString(R.string.about_dev)));
        summaryView.setText(TextUtils.concat(aboutHtml, "\n\n\n\n", aboutDev));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { onBackPressed(); }
        return super.onOptionsItemSelected(item);
    }
}