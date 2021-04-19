package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipespageActivity extends AppCompatActivity {

    ListView lv;
    ProductsAdapter productsAdapter;
    ArrayList<Recipe> recipes;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipespage);
        Intent intent = getIntent();
        String s = intent.getExtras().getString("category");
        tv=findViewById(R.id.text_view_test);
        tv.setText(s);

        lv=findViewById(R.id.recipes_list_view);

        //productsAdapter = new ProductsAdapter(this,0,0,recipes);
        //lv.setAdapter(productsAdapter);

    }
}