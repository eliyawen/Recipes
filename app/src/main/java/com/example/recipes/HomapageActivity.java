package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class HomapageActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog d;
    private EditText editTextRecipeName, editTextPreparationTime, editTextPreparation;
    private Button btnaddDialog, btnsalad, btnmain, btnpastry, btndessert;
    private LinearLayout linearLayout;
    private ListView ingredientsListView;
    private ArrayList<String> ingredients;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homapage);
        btnsalad=findViewById(R.id.btn_salad);
        btnmain=findViewById(R.id.btn_main);
        btnpastry=findViewById(R.id.btn_pastry);
        btndessert=findViewById(R.id.btn_dessert);
        linearLayout=findViewById(R.id.linearLayout);

        ingredients= new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.two_line_list_item, ingredients);
        ingredientsListView=findViewById(R.id.ingredients_list_view);
        ingredientsListView.setAdapter(arrayAdapter);

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


    //add category
    public void addButton(){
        Button btn = new Button(this);
        LinearLayout.LayoutParams buttonLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayout.setMargins(30, 0, 30, 0);
        btn.setLayoutParams(buttonLayout);
        linearLayout.addView(btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id == R.id.add_recipe){
            //open dialog
            addRecipeDialog();
        }
        if (id == R.id.add_category){
            //creat new category
        }
        return true;
    }

    //add recipe
    public void addRecipeDialog(){
        d= new Dialog(this);
        d.setContentView(R.layout.add_recipe_dialog);
        d.setCancelable(true);
        d.setTitle("הוספת מתכון");
        editTextRecipeName = d.findViewById(R.id.recipe_name);
        editTextPreparationTime = d.findViewById(R.id.preparation_time);
        NumberPicker numberPicker = d.findViewById(R.id.difficulty);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        editTextPreparation = d.findViewById(R.id.preparation_method);
        btnaddDialog = d.findViewById(R.id.btnadd_dialog);
        btnaddDialog.setOnClickListener(this);
        d.show();
    }

}