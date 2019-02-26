package com.nytimes.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nytimes.application.adapter.ArticlesAdapter;
import com.nytimes.application.api.Article;
import com.nytimes.application.api.ApiManager;
import com.nytimes.application.api.Json;
import com.nytimes.application.api.Result;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ArticlesAdapter.OnArticleItemClickListener {

    private RecyclerView rvArticles;
    private List<Result> resultList = new ArrayList<>();
    private ArticlesAdapter articleAdapter;
    private ProgressBar progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvArticles = findViewById(R.id.rv_article);
        progressDialog = findViewById(R.id.progress);

        rvArticles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        articleAdapter = new ArticlesAdapter(getApplicationContext(), resultList, this  );
        rvArticles.setAdapter(articleAdapter);

        renderArticles();
    }

    private void renderArticles() {
        ApiManager apiManager = new ApiManager();
        apiManager.getArticles("7", new ApiManager.ApiCallback<Article>() {
            @Override
            public void success(Article response) {
                renderData(response);
            }

            @Override
            public void failure(int responseCode) {
                Toast.makeText(getApplicationContext(), "There was problem with server. Please try again later.", Toast.LENGTH_LONG).show();
                progressDialog.setVisibility(View.GONE);
            }
        });
    }

    private void renderData(Article response) {
        progressDialog.setVisibility(View.GONE);
        rvArticles.setVisibility(View.VISIBLE);
        if (response.getResults() != null && !response.getResults().isEmpty()) {
            resultList.clear();
            resultList.addAll(response.getResults());
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(Result article) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("article", Json.gson().toJson(article));
        startActivity(intent);
    }
}
