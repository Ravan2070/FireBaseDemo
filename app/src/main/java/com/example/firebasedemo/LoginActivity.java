package com.example.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private EditText mEmail, mPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mLoginButton = findViewById(R.id.loginButton2);
        mEmail = findViewById(R.id.emailEditText2);
        mPassword = findViewById(R.id.passwordEditText2);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pwd = mPassword.getText().toString();

                if (TextUtils.isEmpty(email) == true || TextUtils.isEmpty(pwd) == true) {

                    Toast toast = Toast.makeText(LoginActivity.this, "Email or password cannot be empty!!", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    loginUser(email, pwd);
                }
            }
        });
    }

    private void loginUser(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast toast=Toast.makeText(LoginActivity.this,"Login sucessful",Toast.LENGTH_SHORT);
                toast.show();
                Intent intent=new Intent(LoginActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}