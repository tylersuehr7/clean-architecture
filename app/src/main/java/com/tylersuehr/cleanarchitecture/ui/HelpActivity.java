package com.tylersuehr.cleanarchitecture.ui;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.transition.TransitionInflater;
import android.widget.TextView;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.ui.adapters.HelpAdapter;
import com.tylersuehr.cleanarchitecture.ui.models.HelpHeader;
import com.tylersuehr.cleanarchitecture.ui.models.HelpItem;
import com.tylersuehr.cleanarchitecture.ui.utils.SpanUtils;
import com.tylersuehr.cleanarchitecture.ui.views.CardSpacer;
import com.tylersuehr.cleanarchitecture.ui.views.ElasticFrameLayout;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/30/2016.
 */
public class HelpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(R.transition.about_enter);
        setReturnTransition(R.transition.about_return);
        setContentView(R.layout.activity_help);

        // Check if API 21+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showView();
        } else {
            showLegacyView();
        }
    }

    private void showView() {
        // Set description text
        TextView textView = (TextView)findViewById(R.id.description);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        Spanned about = Html.fromHtml(getString(R.string.about));
        Spanned dev = Html.fromHtml(getString(R.string.about_dev));
        SpannableString devSpan = SpanUtils.createCenterSpan(dev);
        textView.setText(TextUtils.concat(about, "\n\n\n", devSpan));

        // Finish the activity when dragged all the way
        final ElasticFrameLayout dragFrame = (ElasticFrameLayout)findViewById(R.id.drag_container);
        dragFrame.addListener(new ElasticFrameLayout.SystemChromeFader(this) {
            @Override
            @TargetApi(21)
            public void onDragDismissed() {
                // If we drag dismiss downward then the default reversal of the enter
                // transition would slide content upward which looks weird. So reverse it.
                if (dragFrame.getTranslationY() > 0) {
                    getWindow().setReturnTransition(TransitionInflater.from(HelpActivity.this)
                            .inflateTransition(R.transition.about_return_down));
                }
                finishAfterTransition();
            }
        });
    }

    private void showLegacyView() {
        // Setup recycler
        final HelpAdapter adapter = new HelpAdapter();
        RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new CardSpacer());
        recycler.setAdapter(adapter);

        // Add card for developer
        adapter.add(new HelpHeader(getString(R.string.about_dev)));
        adapter.add(new HelpItem("About this App", getString(R.string.about)));
    }
}