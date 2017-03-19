package com.android.saltside.module;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.saltside.R;
import com.android.saltside.SaltSideApp;
import com.android.volley.toolbox.NetworkImageView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViewsAndPopulateData();
    }

    private void initViewsAndPopulateData(){

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        if(null != intent) {
            ((TextView) findViewById(R.id.titleTv)).setText(intent.getStringExtra("title"));
            ((TextView) findViewById(R.id.descTv)).setText(intent.getStringExtra("description"));
            String url = intent.getStringExtra("image");
            if(url != null && url.startsWith("http")){
                url = url.replace("http", "https");
            }
            ((NetworkImageView) findViewById(R.id.imageView)).setImageUrl(url, SaltSideApp.getInstance().getImageLoader());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
