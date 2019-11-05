package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText name = findViewById(R.id.name);
        final EditText uname = findViewById(R.id.uname);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText cpassword = findViewById(R.id.cpassword);
        final Button signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    name.setError("Please enter name");
                    return;
                }
                name.setError(null);

                if(uname.getText().toString().isEmpty()){
                    uname.setError("Please enter username");
                    return;
                }
                uname.setError(null);

                if(email.getText().toString().isEmpty()){
                    email.setError("Please enter Email");
                    return;
                }
                email.setError(null);

                if(password.getText().toString().isEmpty()){
                    password.setError("Please enter Password");
                    return;
                }
                password.setError(null);

                if(cpassword.getText().toString().isEmpty()){
                    cpassword.setError("Please Confirm Password");
                    return;
                }
                cpassword.setError(null);

                if(!password.getText().toString().equals(cpassword.getText().toString())){
                    cpassword.setError("Confirm Password Must Match Password");
                    return;
                }
                cpassword.setError(null);
                signUp(name.getText().toString(),uname.getText().toString(),email.getText().toString(),password.getText().toString());

            }
        });
    }

    void showMessage(String title, String message) {
        new AlertDialog.Builder(SignUpActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(android.R.string.no, null).show();
    }

    void signUp(final String name, final String uname, final String email, final String password){
        FirebaseDatabase.getInstance().getReference("users").child(uname)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            showMessage("Username Already Exists","Please try with different username");
                        }else{
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uname);
                            ref.child("name").setValue(name);
                            ref.child("username").setValue(uname);
                            ref.child("email").setValue(email);
                            ref.child("password").setValue(password);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
