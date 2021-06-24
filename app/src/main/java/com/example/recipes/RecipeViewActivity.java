package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class RecipeViewActivity extends AppCompatActivity {

    private LinearLayout bigLl, smallLl;
    private ScrollView sv;
    private TextView recipeNameTv, difficultyTv, timeTv, preparationMethodTv;
    private TableLayout tableLayout;
    private ImageView recipeImageTv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceCategories;
    private StorageReference storageReference;
    private String recipeName, categoryName;
    private Recipe r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceCategories = firebaseDatabase.getReference("PublicCategories");
        storageReference = FirebaseStorage.getInstance().getReference("Images");

        setContentView(R.layout.activity_recipe_view);
        bigLl=findViewById(R.id.recipe_show_big_ll);
        smallLl=findViewById(R.id.recipe_show_small_ll);
        sv=findViewById(R.id.recipe_show_scroll_view);
        recipeNameTv=findViewById(R.id.recipe_name_tv);
        difficultyTv=findViewById(R.id.difficulty_tv);
        timeTv=findViewById(R.id.time_tv);
        preparationMethodTv=findViewById(R.id.preparation_method_tv);
        tableLayout=findViewById(R.id.table_layout_ingredients_show);
        recipeImageTv=findViewById(R.id.recipe_image_tv);

        Intent intent= getIntent();
        recipeName = intent.getExtras().getString("recipeName");
        categoryName = intent.getExtras().getString("categoryName");

        databaseReferenceCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()){
                    Category c = d.getValue(Category.class);
                    if(c.getCategoryName().equals(categoryName)){//finding the right category
                        for (int i = 0;i<c.getRecipes().size();i++){
                            if(c.getRecipes().get(i).getRecipeName().equals(recipeName)){//finding the right recipe
                                r = c.getRecipes().get(i);
                            }
                        }

                        if (d.getKey().equals(categoryName)){
                            categoryName = "המתכונים שלי";
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //setting all the TextViews
        setTitle(categoryName);
        recipeNameTv.setText(recipeName);
        timeTv.setText(r.getTime());
        difficultyTv.setText(r.getDifficulty());
        //adding the ingredients
        for (Map.Entry pairEntry: r.getIngredients().entrySet()){

            TableRow tr = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            TextView tvItem = new TextView(this);
            tvItem.setText((Integer) pairEntry.getKey());
            tvItem.setPadding(80,0,0,0);
            lp.setMargins(50,0,0,0);
            tvItem.setLayoutParams(lp);

            TextView tvAmount = new TextView(this);
            tvAmount.setHint((Integer) pairEntry.getValue());
            tvAmount.setPadding(80,0,0,0);
            lp.setMargins(100,0,0,0);
            tvAmount.setLayoutParams(lp);
            tr.addView(tvAmount);
            tr.addView(tvItem);
            tableLayout.addView(tr);
            smallLl.removeView(tableLayout);
            smallLl.addView(tableLayout,4);
            sv.removeAllViews();
            sv.addView(smallLl);
            bigLl.removeAllViews();
            bigLl.addView(sv);
        }
        preparationMethodTv.setText(r.getPreparationMethod());
        //adding the image
        storageReference.child(intent.getExtras().getString("key")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(RecipeViewActivity.this).load(uri).into(recipeImageTv);
            }
        });

    }
}