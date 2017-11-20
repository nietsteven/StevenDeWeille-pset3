package com.example.stevendeweillepset3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();

        bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_order:
                        break;
                }
                return false;
            }
        });

        TextView total_items = (TextView) findViewById(R.id.total_items);
        TextView total_price = (TextView) findViewById(R.id.total_price);
        TextView order_placed = (TextView) findViewById(R.id.order_placed);
        int tot_price = 0;
        for (int i = 0; i<MainActivity.ORDER.size(); i++) {
            String price = MainActivity.ORDER.get(i).second.toString();
            price = price.substring(0, price.length()-2);
            tot_price += Integer.valueOf(price);
        }
        total_price.setText("TOTAL PRICE: $"+tot_price);
        String items = Integer.toString(MainActivity.ORDER.size());
        total_items.setText("You currently have "+items+ " items in your order.");
    }
}
