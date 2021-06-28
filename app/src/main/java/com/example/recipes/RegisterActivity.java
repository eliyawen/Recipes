package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etRegisterEmailadress, etRegisterpassword, etRegisterConfirmpassword;
    private Button btnsignup;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseReferenceCategories;
    private FirebaseDatabase firebaseDatabase;
    private TextView regTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MatcoNet");
        setContentView(R.layout.activity_register);
        etRegisterEmailadress=findViewById(R.id.etRegisterEmailadress);
        etRegisterpassword=findViewById(R.id.etRegisterpassword);
        etRegisterConfirmpassword=findViewById(R.id.etRegisterConfirmpassword);
        btnsignup=findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(this);
        regTv=findViewById(R.id.regTv);

        btnsignup.setBackgroundColor(Color.BLUE);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        databaseReferenceCategories = firebaseDatabase.getReference("PublicCategories");


    }

    @Override
    public void onClick(View v) {
        if(v==btnsignup){
           register();
        }
    }

    public void register(){
        String email = etRegisterEmailadress.getText().toString().trim();
        String password =etRegisterpassword.getText().toString().trim();
        String confirmpass =etRegisterConfirmpassword.getText().toString().trim();

        if(!password.equals(confirmpass)){
            regTv.setText("The password and the confirmPassword must be identical");
            etRegisterpassword.setText("");
            etRegisterConfirmpassword.setText("");
            return;
        }

        if (password.length() < 5){
           regTv.setText("The password must contains at least 5 charecters");
            etRegisterpassword.setText("");
            etRegisterConfirmpassword.setText("");
            return;
        }

        //creating new user on the firebaseAuth
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){//sign in successfuly
                    //adding user to the database
                    User user = new User(etRegisterpassword.getText().toString());
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                    //adding unique category to the user(for "myCategory")
                    Category category = new Category(firebaseAuth.getCurrentUser().getUid());
                    databaseReferenceCategories.child(firebaseAuth.getCurrentUser().getUid()).setValue(category);

                    Intent intent = new Intent(RegisterActivity.this, HomapageActivity.class);
                    startActivity(intent);
                } else{
                    //sign in faild
                    regTv.setText("registration fails");
                    return;
                }
            }
        });



    }
}