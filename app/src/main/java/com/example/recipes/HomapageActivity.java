package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomapageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnsalad, btnmain, btnpastry, btndessert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homapage);
        btnsalad=findViewById(R.id.btnsalad);
        btnmain=findViewById(R.id.btnmain);
        btnpastry=findViewById(R.id.btnpastry);
        btndessert=findViewById(R.id.btndessert);

        btnsalad.setOnClickListener(this);
        btnmain.setOnClickListener(this);
        btnpastry.setOnClickListener(this);
        btndessert.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnsalad){

        }
        if (v==btnmain){

        }
        if(v==btnpastry){

        }
        if (v==btndessert){

        }
        Intent intent = new Intent(this, RecipespageActivity.class);
    }
}