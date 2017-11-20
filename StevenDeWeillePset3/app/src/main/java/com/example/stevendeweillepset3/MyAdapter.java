package com.example.stevendeweillepset3;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

// MyAdapter.java (separate Java class)
public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> descriptions;
    ArrayList<String> names;
    ArrayList<String> prices;
    ArrayList<String> image_urls;
    LayoutInflater inflater;
    String dishname;
    String dishprice;
    SharedPreferences prefs;
    public MyAdapter(Context context, ArrayList<String> names, ArrayList<String> descriptions,
                     ArrayList<String> prices, ArrayList<String> image_urls) {

        super(context, R.layout.mylistlayout);

        this.context= context;
        this.names = names;
        this.descriptions = descriptions;
        this.prices = prices;
        this.image_urls = image_urls;
        System.out.println(descriptions);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        System.out.println("test");
        view = inflater.inflate(R.layout.mylistlayout, parent, false);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView price = (TextView) view.findViewById(R.id.price);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        dishname = this.names.get(index);
        dishprice = this.prices.get(index);
        name.setText(dishname);
        description.setText(this.descriptions.get(index));
        price.setText("$"+dishprice);
        Glide.with(context).load(this.image_urls.get(index)).into(image);

        Button addButton = (Button) view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Pair item = new Pair(dishname,dishprice);
                MainActivity.ORDER.add(item);
                System.out.println(MainActivity.ORDER);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return names.size();
    }

}