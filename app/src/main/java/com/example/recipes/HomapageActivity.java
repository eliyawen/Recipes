package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class HomapageActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog d;
    private EditText editTextRecipeName, editTextPreparationTime, editTextPreparation, editTextCategoryNameDialog;
    private Button btnaddDialog, btnsalad, btnmain, btnpastry, btndessert, btnAddCategoryDialog;
    private LinearLayout linearLayout;
    private ListView ingredientsListView;
    //private TableLayout tableLayoutIngredients;
    private Button btnAddIngredientsRow;
    private TableLayout tableLayoutIngredients;
    private LinearLayout bigLlDialog, smallLlDialog;
    private ScrollView scrollViewDialog;
    private ArrayList<Button> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homapage);
        btnsalad=findViewById(R.id.btn_salad);
        btnmain=findViewById(R.id.btn_main);
        btnpastry=findViewById(R.id.btn_pastry);
        btndessert=findViewById(R.id.btn_dessert);
        linearLayout=findViewById(R.id.linearLayout);


        btnsalad.setOnClickListener(this);
        btnmain.setOnClickListener(this);
        btnpastry.setOnClickListener(this);
        btndessert.setOnClickListener(this);

        buttons= new ArrayList<Button>();
        buttons.add(btnmain);
        buttons.add(btndessert);
        buttons.add(btnpastry);
        buttons.add(btnsalad);

    }

    @Override
    public void onClick(View v) {

        if(v == btnAddIngredientsRow) addIngredientsRowFunction();
        if(v == btnaddDialog){
            //adding recipe to the arraylist
            d.dismiss();
        }
        if(v == btnAddCategoryDialog){
            addCategoryButton(editTextCategoryNameDialog.getText().toString());
            d.dismiss();
        }
        else{
            Intent intent = new Intent(this, RecipespageActivity.class);
            for(Button b:buttons) {
                if (v==b){
                    intent.putExtra("category",b.getText());
                    startActivity(intent);
            }
            }
        }
    }


    //add category button
    public void addCategoryButton(String categoryName){
        Button btn = new Button(this);
        LinearLayout.LayoutParams buttonLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayout.setMargins(30, 0, 30, 0);
        btn.setLayoutParams(buttonLayout);
        btn.setPadding(0,15,0,15);
        btn.setText(categoryName);
        linearLayout.addView(btn);
        buttons.add(btn);
        //add the new category to the user
        btn.setOnClickListener(this);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    //menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id == R.id.add_recipe){
            //open add recipe dialog
            addRecipeDialog();
        }
        if (id == R.id.add_category){
            //open add category dialog
            addCategoryDialog();
        }
        return true;
    }

    //add recipe dialog
    public void addRecipeDialog(){
        d= new Dialog(this);
        d.setContentView(R.layout.add_recipe_dialog);
        d.setCancelable(true);
        tableLayoutIngredients = d.findViewById(R.id.table_layout_ingredients);
        bigLlDialog = d.findViewById(R.id.big_linear_layout_dialog);
        scrollViewDialog = d.findViewById(R.id.scroll_view_dialog);
        smallLlDialog = d.findViewById(R.id.small_linear_layout_dialog);
        d.setTitle("הוספת מתכון");
        editTextRecipeName = d.findViewById(R.id.recipe_name);
        editTextPreparationTime = d.findViewById(R.id.preparation_time);
        NumberPicker numberPicker = d.findViewById(R.id.difficulty);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        editTextPreparation = d.findViewById(R.id.preparation_method);
        btnaddDialog = d.findViewById(R.id.btn_add_dialog);
        btnaddDialog.setOnClickListener(this);
        btnAddIngredientsRow=d.findViewById(R.id.btn_add_ingredients_row);
        btnAddIngredientsRow.setOnClickListener(this);
        d.show();
    }

    //add ingredients row in dialog
    public void  addIngredientsRowFunction(){

        TableRow tr = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        EditText etItem = new EditText(this);
        etItem.setHint("מצרך");
        etItem.setPadding(80,0,0,0);
        lp.setMargins(50,0,0,0);
        etItem.setLayoutParams(lp);

        EditText etAmount = new EditText(this);
        etAmount.setHint("כמות");
        etAmount.setPadding(80,0,0,0);
        lp.setMargins(100,0,0,0);
        etAmount.setLayoutParams(lp);
        tr.addView(etAmount);
        tr.addView(etItem);
        tableLayoutIngredients.addView(tr);
        smallLlDialog.removeView(tableLayoutIngredients);
        smallLlDialog.addView(tableLayoutIngredients,4);
        scrollViewDialog.removeAllViews();
        scrollViewDialog.addView(smallLlDialog);
        bigLlDialog.removeAllViews();
        bigLlDialog.addView(scrollViewDialog);

    }

    //add category dialog
    public void addCategoryDialog(){
        d = new Dialog(this);
        d.setContentView(R.layout.add_category_dialog);
        d.setCancelable(true);
        editTextCategoryNameDialog = d.findViewById(R.id.et_category_name_dialog);
        btnAddCategoryDialog = d.findViewById(R.id.btn_add_category_dialog);
        btnAddCategoryDialog.setOnClickListener(this);
        d.show();
    }



}