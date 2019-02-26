package com.nytimes.application;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nytimes.application.api.Json;
import com.nytimes.application.api.Result;

import com.nytimes.application.databinding.DetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.details);

        ImageView ivImage = findViewById(R.id.iv_article_image);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        String article = getIntent().getStringExtra("article");
        Result result = Json.gson().fromJson(article, Result.class);
        Log.e(DetailsActivity.class.getName(), result.getTitle());
        binding.setArticle(result);

        try {
            Glide.with(getApplicationContext()).load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(ivImage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
