package com.example.prototype23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Regestration extends AppCompatActivity {

    Button reg;
    EditText username, email, contact, password;
    TextView signIn;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        reg = findViewById(R.id.regBtn);
        username = findViewById(R.id.usernameReg);
        email = findViewById(R.id.emailReg);
        contact = findViewById(R.id.contactReg);
        password = findViewById(R.id.passwordReg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname = username.getText().toString().trim();
                final String uemail = email.getText().toString().trim();
                final String ucontact = contact.getText().toString().trim();
                final String upass = password.getText().toString().trim();

                if(uname.isEmpty() || uemail.isEmpty() || ucontact.isEmpty() || upass.isEmpty()){
                    Toast.makeText(Regestration.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(uname)){
                                Toast.makeText(Regestration.this, "Username Already exists", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                reference.child("Users").child(uname).child("email").setValue(uemail);
                                reference.child("Users").child(uname).child("phone").setValue(ucontact);
                                reference.child("Users").child(uname).child("password").setValue(upass);
                                reference.child("Users").child(uname).child("username").setValue(uemail);
                                Toast.makeText(Regestration.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Regestration.this, "Registration Unsuccessfull", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}