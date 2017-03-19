package com.android.saltside.module;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.saltside.R;
import com.android.saltside.SaltSideApp;
import com.android.saltside.model.ItemData;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListItemsActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener, ListItemAdapter.ItemClickListener{

    private static final String BASE_URL = "https://gist.githubusercontent.com/maclir/f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json";
    private RecyclerView listRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        initViews();
        fetchData();
    }

    private void initViews(){
        listRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        listRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void fetchData(){
        JsonArrayRequest request = new JsonArrayRequest(BASE_URL, this, this);
        SaltSideApp.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressBar.setVisibility(View.INVISIBLE);
        Snackbar.make(findViewById(R.id.coordinatorLayout), "Server Error! Please try again", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        parseAndSetData(response.toString());
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void parseAndSetData(String response){
        Gson gson = new Gson();
        Type type = new TypeToken<List<ItemData>>() {}.getType();
        ArrayList<ItemData> dataArrayList = gson.fromJson(response, type);
        listRecyclerView.setAdapter(new ListItemAdapter(dataArrayList, this));
    }

    @Override
    public void onItemClick(int position) {
        if(listRecyclerView.getAdapter() instanceof  ListItemAdapter) {
            ListItemAdapter adapter = (ListItemAdapter) listRecyclerView.getAdapter();
            ItemData itemData = adapter.getItem(position);
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("title", itemData.getTitle());
            intent.putExtra("description", itemData.getDescription());
            intent.putExtra("image", itemData.getImageUrl());
            startActivity(intent);
        }
    }
}
