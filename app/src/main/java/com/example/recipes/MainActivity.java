package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnsignin, btnsignup, btnforgot;
    private EditText etEmailLogin, etPasswordLogin;
    private FirebaseAuth firebaseAuth;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmailLogin=findViewById(R.id.etEmailLogin);
        etPasswordLogin=findViewById(R.id.etPasswordLogin);
        btnsignin=findViewById(R.id.btnsignin);
        btnsignup=findViewById(R.id.btnsignup);
        btnforgot=findViewById(R.id.btnforgot);
        btnsignin.setOnClickListener(this);
        btnsignup.setOnClickListener(this);
        tvLogin=findViewById(R.id.tvLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(this,HomapageActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if(btnsignin == v){
            login();
        }

        if(btnsignup == v){
           Intent intent = new Intent(this, RegisterActivity.class);
           startActivity(intent);
        }

        if (btnforgot==v){
            //change password
        }
    }

    public void login(){
        String email = etEmailLogin.getText().toString().trim();
        String password = etPasswordLogin.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //log in successfuly
                    Intent intent = new Intent(MainActivity.this,HomapageActivity.class);
                    startActivity(intent);
                } else{
                    //fail in login
                    tvLogin.setText("Email or password are incorrect");
                }
            }
        });
    }
}
