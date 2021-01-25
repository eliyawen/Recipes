package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailadress, name, password, conpassword;
    private Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailadress=findViewById(R.id.emailadress);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        conpassword=findViewById(R.id.conpassword);
        btnsignup=findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==btnsignup){
            String email = emailadress.getText().toString();
            String namestr =name.getText().toString();
            String pass =password.getText().toString();
            String conpass =conpassword.getText().toString();
            if (!pass.equals(conpass)){
                Snackbar.make(findViewById(android.R.id.content), "your password didnt match each other", Snackbar.LENGTH_SHORT).show();
            }
            else{
                //put the name and password in the database
                Intent intent = new Intent(this, HomapageActivity.class);
                startActivity(intent);
            }
        }
    }
}