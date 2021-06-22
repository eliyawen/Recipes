package com.example.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ProductsAdapter extends ArrayAdapter<Recipe> {
    Context context;
    List<Recipe> recipes;

    public ProductsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Recipe> recipes) {
        super(context, resource, textViewResourceId, recipes);
    }


    public View getView(int position, View convertView, ViewGroup parent){
        Recipe recipe = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, parent, false);
        }
        //connecting between the custom layout and the recipe values
        TextView recipeName = convertView.findViewById(R.id.recipe_name_TextView);
        ImageView recipeImage = convertView.findViewById(R.id.recipe_image);

        recipeName.setText(String.valueOf(recipe.getRecipeName()));


        return convertView;
    }
}
