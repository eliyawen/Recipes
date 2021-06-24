package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecipespageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ProductsAdapter productsAdapter;
    ArrayList<Recipe> recipes;
    String categoryName;
    SearchView searchView;
    ArrayList<String> recipesNamesList;
    ArrayAdapter<String> adapter;

    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    DatabaseReference databaseReferenceCategories, databaseReferenceUsers;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUsers = firebaseDatabase.getReference("Users");
        databaseReferenceCategories = firebaseDatabase.getReference("PublicCategories");
        storageReference = FirebaseStorage.getInstance().getReference("Images");

        setContentView(R.layout.activity_recipespage);
        lv=findViewById(R.id.recipes_list_view);

        Intent intent = getIntent();
        categoryName = intent.getExtras().getString("category");

        recipes = new ArrayList<Recipe>();
        recipesNamesList = new ArrayList<String>();


        //for the custom layout
        databaseReferenceCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    if (d.getValue(Category.class).getCategoryName().equals(categoryName)) {
                        recipes = d.getValue(Category.class).getRecipes();
                        setTitle(categoryName);
                    }
                    if(categoryName.length()>20){
                        setTitle("המתכונים שלי");
                    }
                }
                productsAdapter = new ProductsAdapter(RecipespageActivity.this,0,0,recipes);
                lv.setAdapter(productsAdapter);


                //for the search view
                for (int i = 0;i<recipes.size();i++){
                    int temp = i;
                    recipesNamesList.add(recipes.get(temp).getRecipeName());
                }

                adapter = new ArrayAdapter<String>(RecipespageActivity.this, android.R.layout.simple_expandable_list_item_1, recipesNamesList);
                lv.setAdapter(adapter);

                searchView = findViewById(R.id.search_view);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchView.clearFocus();
                        if (recipesNamesList.contains(query)){
                            adapter.getFilter().filter(query);
                            Toast.makeText(getApplicationContext(),"item found",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"item not found",Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        lv.setOnItemClickListener(this);

        }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(RecipespageActivity.this,RecipeViewActivity.class);
        Recipe r = recipes.get(position);
        intent.putExtra("recipeName",r.getRecipeName());
        intent.putExtra("categoryName",categoryName);
        startActivity(intent);
    }
}