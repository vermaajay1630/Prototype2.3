package com.example.prototype23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button login;
    EditText username, password;
    TextView signup, passwordReset;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.signUp);
        passwordReset = findViewById(R.id.forgotPassword);
        username = findViewById(R.id.usernameLog);
        password = findViewById(R.id.passwordLog);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname = username.getText().toString().trim();
                final String upass = password.getText().toString().trim();
                if(uname.isEmpty() || upass.isEmpty()){
                    Toast.makeText(Login.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                }
                else{
                    reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(uname)){
                                final String getPass = snapshot.child(uname).child("password").getValue(String.class);
                                if(getPass.equals(upass)) {
                                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                    main.putExtra("username", uname);
                                    startActivity(main);
                                }else{
                                    Toast.makeText(Login.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(getApplicationContext(), Regestration.class);
                startActivity(sign);
            }
        });
    }
}