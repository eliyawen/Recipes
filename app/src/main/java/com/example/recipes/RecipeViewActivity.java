package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class RecipeViewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView recipeNameTv, difficultyTv, timeTv, preparationMethodTv, ingredientsTv;
    private ImageView recipeImageTv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceCategories;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private String recipeName, categoryName;
    private Recipe r;
    private Button addingRecipeToMeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceCategories = firebaseDatabase.getReference("PublicCategories");
        storageReference = FirebaseStorage.getInstance().getReference("Images");

        setContentView(R.layout.activity_recipe_view);
        addingRecipeToMeBtn = findViewById(R.id.adding_recipe_to_me_btn);
        recipeNameTv=findViewById(R.id.recipe_name_tv);
        difficultyTv=findViewById(R.id.difficulty_tv);
        timeTv=findViewById(R.id.time_tv);
        preparationMethodTv=findViewById(R.id.preparation_method_tv);
        recipeImageTv=findViewById(R.id.recipe_image_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        addingRecipeToMeBtn.setBackgroundColor(Color.BLUE);
        addingRecipeToMeBtn.setOnClickListener(this);

        Intent intent= getIntent();
        recipeName = intent.getExtras().getString("recipeName");
        categoryName = intent.getExtras().getString("recipeName");

        //getting the wanted recipe from the database, using the exactly recipeName and recipeName that we got on the intent
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

                //setting all the TextViews
                setTitle(categoryName);
                recipeNameTv.setText(recipeName);
                timeTv.setText(r.getTime());
                difficultyTv.setText(String.valueOf(r.getDifficulty()));
                ingredientsTv.setText(r.getIngredients());
                preparationMethodTv.setText(r.getPreparationMethod());
                //adding the image
                storageReference.child(r.getKey()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(RecipeViewActivity.this).load(uri).into(recipeImageTv);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == addingRecipeToMeBtn){
            databaseReferenceCategories.child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //when the user wants to add the recipe to his own recipes
                    Category category = snapshot.getValue(Category.class);
                    category.addRecipe(r);
                    databaseReferenceCategories.child(firebaseAuth.getCurrentUser().getUid()).setValue(category);
                    Toast.makeText(getApplicationContext(),"המתכון נוסף בהצלחה",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}