package com.example.recipes;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ProductsAdapter extends ArrayAdapter<Recipe> {
    Context context;
    List<Recipe> recipes;

    private StorageReference storageReference;

    public ProductsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Recipe> recipes) {
        super(context, resource, textViewResourceId, recipes);

        this.context=context;
        this.recipes = recipes;
    }


    //filing everytime the custom layout with getView. connecting between the arraylist and the listView
    public View getView(int position, View convertView, ViewGroup parent){
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        Recipe recipe = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, parent, false);
        }
        //connecting between the custom layout and the recipe values
        TextView recipeName = convertView.findViewById(R.id.recipe_name_TextView);
        ImageView recipeImage = convertView.findViewById(R.id.recipe_image);


        recipeName.setText(String.valueOf(recipe.getRecipeName()));
        //image from storage
        storageReference.child(recipe.getKey()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(recipeImage);
                Log.d("msg", "image loaded");
            }
        });

        return convertView;
    }
}
