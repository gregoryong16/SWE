package com.example.cz2006.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cz2006.MainActivity;
import com.example.cz2006.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText mFullName,mEmail,mPhone, mPassword;
    private Button mRegisterBtn;
    private TextView mLoginBtn;
    private FirebaseAuth fAuth;
    private ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();

        mFullName= findViewById(R.id.FullName);
        mRegisterBtn = (Button) findViewById(R.id.registerbtn);
        mEmail = (EditText) findViewById(R.id.EmailAddress);
        mPhone= (EditText) findViewById(R.id.phone);
        mPassword = (EditText) findViewById(R.id.Password);
        mLoginBtn = findViewById(R.id.Loginlink);
        getSupportActionBar().hide();

        progBar= findViewById(R.id.progressBar2);

        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }
                progBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Email has already been used!", Toast.LENGTH_SHORT).show();
                            progBar.setVisibility(View.INVISIBLE);
                        }

                    }

                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}