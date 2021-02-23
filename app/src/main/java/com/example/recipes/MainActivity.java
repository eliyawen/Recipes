package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnsignin, btnsignup, btnforgot;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btnsignin=findViewById(R.id.btnsignin);
        btnsignup=findViewById(R.id.btnsignup);
        btnforgot=findViewById(R.id.btnforgot);
        btnsignin.setOnClickListener(this);
        btnsignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(btnsignin == v){
            String user= username.getText().toString();
            String pass= password.getText().toString();
            //check username and password
            Intent intent = new Intent(this, HomapageActivity.class);
            startActivity(intent);
        }

        if(btnsignup == v){
//            Intent intent = new Intent(this, RegisterActivity.class);
//            startActivity(intent);
        }

        if (btnforgot==v){
            //change password
        }
    }
}
