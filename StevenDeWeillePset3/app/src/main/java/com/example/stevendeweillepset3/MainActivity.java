package com.example.stevendeweillepset3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
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

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> CATEGORIES = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView mTextView = (TextView) findViewById(R.id.error);

        bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        break;
                    case R.id.menu_order:
                        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                        intent.putExtra("order", ORDER);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        adapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        CATEGORIES);

        ListView list = (ListView) findViewById(R.id.start_menu);
        list.setAdapter(adapter);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/categories";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray categories = response.getJSONArray("categories");
                        for (int i=0; i<categories.length(); i++) {
                            CATEGORIES.add(categories.getString(i).toUpperCase());
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        mTextView.setText("Oops. Something went wrong.");
                    }
                }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("Error. No internet connection found.");
            }
        }

        );
        queue.add(jsonRequest);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapter, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DishesActivity.class);
                intent.putExtra("category", adapter.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
    }
    public static ArrayList<Pair> ORDER = new ArrayList<Pair>();
}
