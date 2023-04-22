package com.example.recept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private static final String LOG_TAG = Register.class.getName();

    EditText nameET;
    EditText emailET;
    EditText passwordET;
    EditText passwordAgainET;

    MaterialButton loginbtn;
    MaterialButton registerbtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameET = findViewById(R.id.name);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        passwordAgainET = findViewById(R.id.passwordAgain);

        loginbtn = findViewById(R.id.loginbtn);
        registerbtn = findViewById(R.id.registerbtn);

        mAuth = FirebaseAuth.getInstance();

        register();
        login();
    }

    public void register(){
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String passwordAgain = passwordAgainET.getText().toString();
                if(password.equals(passwordAgain)){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(LOG_TAG, "Sikeres regisztráció");
                            }else{
                                Log.d(LOG_TAG, "Valami nem jó");
                                Toast.makeText(Register.this, "Valami nem jó" + task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this,"Passwords don't match!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login(){
        Intent intent = new Intent(this, MainActivity.class);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}