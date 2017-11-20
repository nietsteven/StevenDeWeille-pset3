package com.example.stevendeweillepset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DishesActivity extends AppCompatActivity {
    ArrayList<String> NAMES = new ArrayList<String>();
    ArrayList<String> DESCRIPTIONS = new ArrayList<String>();
    ArrayList<String> PRICES = new ArrayList<String>();
    ArrayList<String> IMAGE_URLS = new ArrayList<String>();
    MyAdapter adapter;
    String category;
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);
        SharedPreferences prefs = getSharedPreferences("order", MODE_PRIVATE);
        bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent intent = new Intent(DishesActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_order:
                        Intent intent2 = new Intent(DishesActivity.this, OrderActivity.class);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        TextView mTextView = findViewById(R.id.category);
        mTextView.setText(category);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/menu";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i=0; i<items.length(); i++) {
                                JSONObject dish = items.getJSONObject(i);
                                if (dish.getString("category").equals(category.toLowerCase())) {
                                    NAMES.add(dish.getString("name"));
                                    DESCRIPTIONS.add(dish.getString("description"));
                                    PRICES.add(dish.getString("price"));
                                    IMAGE_URLS.add(dish.getString("image_url"));
                                }
                            }

                            System.out.println(NAMES);
                            ListView list = (ListView) findViewById(R.id.dish_menu);
                            adapter = new MyAdapter(DishesActivity.this, NAMES, DESCRIPTIONS, PRICES, IMAGE_URLS);
                            list.setAdapter(adapter);
                        } catch (JSONException e) {
                            System.out.println("Oops. Something went wrong.");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error. No internet connection found.");
            }
        }

        );
        queue.add(jsonRequest);

    }
}
