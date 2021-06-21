package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.MimeTypeFilter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

public class HomapageActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Dialog d;
    private EditText editTextRecipeName, editTextPreparationTime, editTextPreparation, editTextCategoryNameDialog;
    private Button btnAddDialog, btnAddCategoryDialog;
    private LinearLayout linearLayout;
    private ListView ingredientsListView;
    //private TableLayout tableLayoutIngredients;
    private Button btnAddIngredientsRow, btnAddImage;
    private TableLayout tableLayoutIngredients;
    private LinearLayout bigLlDialog, smallLlDialog;
    private ScrollView scrollViewDialog;
    private ArrayList<Button> buttons;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUsers, databaseReferenceCategories, databaseReferenceCategoriesNames;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private User user;
    private ArrayList<String> categoryNameList;
    private Spinner categorySpinner;
    private Recipe r;
    private String categoryName;
    private Uri imageUri;
    private String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homapage);
        linearLayout=findViewById(R.id.linearLayout);

        buttons= new ArrayList<Button>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUsers = firebaseDatabase.getReference("Users");
        databaseReferenceCategories = firebaseDatabase.getReference("PublicCategories");
        storageReference = FirebaseStorage.getInstance().getReference("Images");

        //createCategoryButtons();
    }

    @Override
    public void onClick(View v) {

        if(v == btnAddImage){
            Intent i =new Intent(Intent.ACTION_GET_CONTENT);
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
            }
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i,"Choose Picture"),0);
        }

        if(v == btnAddIngredientsRow) addIngredientsRowFunction();

        if(v == btnAddDialog){
            //uploadTheImageToStorage
            upload();
            //adding recipe to the category
            r=new Recipe(editTextRecipeName.getText().toString());
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
        String key = System.currentTimeMillis() + "." + getFileExtension(imageUri);
        storageReference.child(key).putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageString = taskSnapshot.getUploadSessionUri().toString();
                        recipe.setImageUri(imageString);
                    }
                });
    }

    public String getFileExtension(Uri uri){
        String cr = getContentResolver().getType(uri);
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        return mtm.getExtensionFromMimeType(cr);
    }

    public void addRecipeToCategory(){
        databaseReferenceCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()) {
                    if(d.getValue(Category.class).getCategoryName().equals(categoryName)){
                        Category category= d.getValue(Category.class);
                        category.addRecipe(r);
                        databaseReferenceCategories.setValue(category);
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
        databaseReferenceCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot category: snapshot.getChildren()){
                    categoryNameList.add(category.getValue(Category.class).getCategoryName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (int i=0;i<categoryNameList.size();i++){
            Button btn = new Button(this);
            LinearLayout.LayoutParams buttonLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonLayout.setMargins(30, 0, 30, 0);
            btn.setLayoutParams(buttonLayout);
            btn.setPadding(0,15,0,15);
            btn.setText(categoryNameList.get(i));
            linearLayout.addView(btn);
            linearLayout.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
            buttons.add(btn);
            btn.setOnClickListener(this);
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
        linearLayout.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        buttons.add(btn);
        btn.setOnClickListener(this);
        //add the new category to the database
        ArrayList<Recipe> arr = new ArrayList<Recipe>();
        Recipe rec = new Recipe("kkk");
        arr.add(rec);
        Category category = new Category(categoryName,arr);
        databaseReferenceCategories.child(firebaseAuth.getCurrentUser().getUid()+System.currentTimeMillis()).setValue(category);
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
            intent.putExtra("category", "myRecipes");
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

        categorySpinner = d.findViewById(R.id.category_spinner);
        categoryNameList = new ArrayList<String>();


        //databaseReferenceUsers.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
          //  @Override
           // public void onDataChange(@NonNull DataSnapshot snapshot) {
             //   User user = snapshot.getValue(User.class);
               // user.setPassword("123456");
                //databaseReferenceUsers.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
            //}

            //@Override
            //public void onCancelled(@NonNull DatabaseError error) {

            //}
        //});

        
        databaseReferenceCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot category: snapshot.getChildren()) {
                    categoryNameList.add(category.getValue(Category.class).getCategoryName());
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

        btnAddIngredientsRow=d.findViewById(R.id.btn_add_ingredients_row);
        btnAddIngredientsRow.setOnClickListener(this);
        editTextPreparation = d.findViewById(R.id.preparation_method);
        btnAddImage = d.findViewById(R.id.btn_add_image);
        btnAddImage.setOnClickListener(this);
        btnAddDialog = d.findViewById(R.id.btn_add_dialog);
        btnAddDialog.setOnClickListener(this);
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
        editTextCategoryNameDialog.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        btnAddCategoryDialog = d.findViewById(R.id.btn_add_category_dialog);
        btnAddCategoryDialog.setOnClickListener(this);
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