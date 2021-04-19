package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class RecipeViewActivity extends AppCompatActivity {

    private LinearLayout bigLl, smallLl;
    private ScrollView sv;
    private TextView recipeNameTv, difficultyTv, durationTv, preparationMethodTv;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        bigLl=findViewById(R.id.recipe_show_big_ll);
        smallLl=findViewById(R.id.recipe_show_small_ll);
        sv=findViewById(R.id.recipe_show_scroll_view);
        recipeNameTv=findViewById(R.id.recipe_name_tv);
        difficultyTv=findViewById(R.id.difficulty_tv);
        durationTv=findViewById(R.id.duration_tv);
        preparationMethodTv=findViewById(R.id.preparation_method_tv);
        tableLayout=findViewById(R.id.table_layout_ingredients_show);

    }
}