package com.example.cz2006.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cz2006.MainActivity;
import com.example.cz2006.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mLoginBtn;
    private TextView mRegisterLink;
    private FirebaseAuth fAuth;
    private ProgressBar progBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();

        mLoginBtn = (Button) findViewById(R.id.Loginbtn);
        mEmail = (EditText) findViewById(R.id.EmailAddress);
        mPassword = (EditText) findViewById(R.id.Password);
        mRegisterLink = findViewById(R.id.RegisterLink);
        progBar= findViewById(R.id.progressBar);
        getSupportActionBar().hide();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required. ");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password is must be more than 6 characters. ");
                    return;
                }
                progBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Invalid user or password!", Toast.LENGTH_SHORT).show();
                            progBar.setVisibility(View.INVISIBLE);
                        }

                    }

                });

                    }
                });
                mRegisterLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), Register.class));
                        finish();
                    }
                });
        }
    }
