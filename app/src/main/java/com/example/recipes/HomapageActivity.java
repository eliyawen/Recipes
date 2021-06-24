package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.MimeTypeFilter;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomapageActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

     ActionBar actionBar;
    private Dialog d;
    private EditText editTextRecipeName, editTextPreparationTime, editTextPreparation, editTextCategoryNameDialog, editTextIngredients;
    private Button btnAddDialog, btnAddCategoryDialog;
    private LinearLayout linearLayout;
    private Button  btnAddImage;
    private ArrayList<Button> buttons;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUsers, databaseReferenceCategories;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private User user;
    private ArrayList<String> categoryNameList;
    private Spinner categorySpinner;
    private Recipe r;
    private String categoryName;
    private Uri imageUri;
    private String imageString;
    private NumberPicker numberPicker;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homapage);
        linearLayout=findViewById(R.id.linearLayout);

        setTitle("MatcoNet");

        buttons= new ArrayList<Button>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUsers = firebaseDatabase.getReference("Users");
        databaseReferenceCategories = firebaseDatabase.getReference("PublicCategories");
        storageReference = FirebaseStorage.getInstance().getReference("Images");

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1000);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        createCategoryButtons();
    }

    @Override
    public void onClick(View v) {

        if(v == btnAddImage){
            Intent i =new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i,"Choose Picture"),0);
        }


        if(v == btnAddDialog){
            //uploadTheImageToStorage
            upload();
            //adding recipe to the category
            String recipeName = editTextRecipeName.getText().toString();
            String prepTime = editTextPreparationTime.getText().toString();
            int dif = numberPicker.getValue();
            String ingredients = editTextIngredients.getText().toString();
            String prep = editTextPreparation.getText().toString();
            r = new Recipe(recipeName,prepTime,dif,imageString,ingredients,prep);
            categoryName = categorySpinner.getSelectedItem().toString();
            addRecipeToCategory();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            imageUri = data.getData();
        }
    }

    public void upload(){
        key = System.currentTimeMillis() + "." + getFileExtension(imageUri);
        storageReference.child(key).putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageString = taskSnapshot.getUploadSessionUri().toString();
                    }
                });
        Toast.makeText(getApplicationContext(),"התמונ נוספה בהצלחה",Toast.LENGTH_LONG).show();
    }

    public String getFileExtension(Uri uri){
        String cr = getContentResolver().getType(uri);
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return mtm.getExtensionFromMimeType(cr);
    }

    public void addRecipeToCategory(){
        databaseReferenceCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()) {
                    if(d.getValue(Category.class).getCategoryName().equals(categoryName)){
                        Category category = d.getValue(Category.class);
                        category.addRecipe(r);
                        //databaseReferenceCategories.child(d.getKey()).child(System.currentTimeMillis()+"").setValue(r);
                        databaseReferenceCategories.child(d.getKey()).setValue(category);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createCategoryButtons(){
        categoryNameList = new ArrayList<String>();
        databaseReferenceCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    if (d.getValue(Category.class).getCategoryName().length() < 20){
                        categoryNameList.add(d.getValue(Category.class).getCategoryName());
                    }

                }
                for (int i=0;i<categoryNameList.size();i++){
                    Button btn = new Button(HomapageActivity.this);
                    LinearLayout.LayoutParams buttonLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    buttonLayout.setMargins(30, 0, 30, 0);
                    btn.setLayoutParams(buttonLayout);
                    btn.setPadding(0,15,0,15);
                    btn.setText(categoryNameList.get(i));
                    linearLayout.addView(btn);
                    linearLayout.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
                    buttons.add(btn);
                    btn.setOnClickListener(HomapageActivity.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        linearLayout.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        buttons.add(btn);
        btn.setOnClickListener(this);
        //add the new category to the database
        Category category = new Category(categoryName);
        databaseReferenceCategories.child(System.currentTimeMillis()+"").setValue(category);
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
        if(id == R.id.my_recipes){
            //show all user recipes
            Intent intent = new Intent(this,RecipespageActivity.class);
            String str = firebaseAuth.getCurrentUser().getUid();
            intent.putExtra("category", str);
            startActivity(intent);
        }
        if(id == R.id.add_recipe){
            //open add recipe dialog
            addRecipeDialog();
        }
        if (id == R.id.add_category){
            //open add category dialog
            addCategoryDialog();
        }
        if(id == R.id.signOut){
            //sign out
            firebaseAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    //add recipe dialog
    public void addRecipeDialog(){
        d= new Dialog(this);
        d.setContentView(R.layout.add_recipe_dialog);
        d.setCancelable(true);
        editTextIngredients = d.findViewById(R.id.et_ingredients);
        d.setTitle("הוספת מתכון");
        editTextRecipeName = d.findViewById(R.id.recipe_name);
        editTextPreparationTime = d.findViewById(R.id.preparation_time);
        numberPicker = d.findViewById(R.id.difficulty);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);

        categorySpinner = d.findViewById(R.id.category_spinner);
        categoryNameList = new ArrayList<String>();

        
        databaseReferenceCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()) {
                    if(d.getValue(Category.class).getCategoryName().length() < 20){
                        categoryNameList.add(d.getValue(Category.class).getCategoryName());
                    }
                }

                Collections.sort(categoryNameList,new sortByName());
                for (int i=0;i<categoryNameList.size();i++)
                    Log.d("on_data_change",categoryNameList.get(i));
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(HomapageActivity.this, android.R.layout.simple_list_item_single_choice,categoryNameList );
                categorySpinner.setAdapter(dataAdapter);
                categorySpinner.setOnItemSelectedListener(HomapageActivity.this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editTextPreparation = d.findViewById(R.id.preparation_method);
        btnAddImage = d.findViewById(R.id.btn_add_image);
        btnAddImage.setOnClickListener(this);
        btnAddImage.setBackgroundColor(Color.BLUE);
        btnAddDialog = d.findViewById(R.id.btn_add_dialog);
        btnAddDialog.setOnClickListener(this);
        btnAddDialog.setBackgroundColor(Color.BLUE);
        d.show();
    }


    //add category dialog
    public void addCategoryDialog(){
        d = new Dialog(this);
        d.setContentView(R.layout.add_category_dialog);
        d.setCancelable(true);
        editTextCategoryNameDialog = d.findViewById(R.id.et_category_name_dialog);
        editTextCategoryNameDialog.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        btnAddCategoryDialog = d.findViewById(R.id.btn_add_category_dialog);
        btnAddCategoryDialog.setOnClickListener(this);
        btnAddCategoryDialog.setBackgroundColor(Color.BLUE);
        d.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}