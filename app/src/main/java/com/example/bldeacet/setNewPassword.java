package com.example.bldeacet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class setNewPassword extends AppCompatActivity {
ImageView back9;
TextInputLayout newPassword,confirmNewPassword;
String _newpassword,_confirmpassword;
ProgressBar progressBar4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_set_new_password);

        back9=findViewById(R.id.back9);
        progressBar4=findViewById(R.id.progressBar4);
        newPassword=findViewById(R.id.new_password);
        confirmNewPassword=findViewById(R.id.confirm_new_password);

        back9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPassword.super.onBackPressed();
            }
        });

    }

    public void callSetNewPassword(View view) {
        _newpassword = newPassword.getEditText().getText().toString().trim();
        _confirmpassword = confirmNewPassword.getEditText().getText().toString().trim();

        checkInternet checkinternet=new checkInternet();
        if(!checkinternet.isConnected(this)){
            Toast.makeText(this,"Please check your internet connection!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateNewPassword() | !validateConfirmPassword()) {

            return;
        }
        else if (_newpassword.equals(_confirmpassword)) {
                progressBar4.setVisibility(View.VISIBLE);



            String _password=confirmNewPassword.getEditText().getText().toString().trim();
            String _phone=getIntent().getStringExtra("forgotphone");


            Query checkStudent = FirebaseDatabase.getInstance().getReference("Student")
                    .orderByChild("phoneNo").equalTo(_phone);
            checkStudent.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String _password=confirmNewPassword.getEditText().getText().toString().trim();
                        String _phone=getIntent().getStringExtra("forgotphone");
                        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Student");
                        reference1.child(_phone).child("password").setValue(_password);
                    }
                    else{
                        String _password=confirmNewPassword.getEditText().getText().toString().trim();
                        String _phone=getIntent().getStringExtra("forgotphone");
                        Query checkFaculty = FirebaseDatabase.getInstance().getReference("Faculty")
                                .orderByChild("fphoneNo").equalTo(_phone);
                        checkFaculty.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String _password=confirmNewPassword.getEditText().getText().toString().trim();
                                    String _phone=getIntent().getStringExtra("forgotphone");
                                    DatabaseReference reference2= FirebaseDatabase.getInstance().getReference("Faculty");
                                    reference2.child(_phone).child("fpassword").setValue(_password);
                                }
                                else {
                                    Toast.makeText(setNewPassword.this,"Error! Try later.",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(setNewPassword.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            progressBar4.setVisibility(View.GONE);
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            Toast.makeText(this,"Password Updated!",Toast.LENGTH_SHORT).show();
            finish();

        }
        else{
            Toast.makeText(this,"Password does not match!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateNewPassword() {
        _newpassword = newPassword.getEditText().getText().toString().trim();
        String checkPassword = "(?=\\S+$)" +
                ".{4,}" +
                "$";
        if (_newpassword.isEmpty()) {
            newPassword.setError("Field can not be empty");
            return false;
        } else if (!_newpassword.matches(checkPassword)) {
            newPassword.setError("Password should contain atleast 4 characters!");
            return false;

        } else {
            newPassword.setError(null);
            newPassword.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateConfirmPassword() {
        _confirmpassword = confirmNewPassword.getEditText().getText().toString().trim();
        String checkPassword = "(?=\\S+$)" +
                ".{4,}" +
                "$";
        if (_confirmpassword.isEmpty()) {
            confirmNewPassword.setError("Field can not be empty");
            return false;
        } else if (!_confirmpassword.matches(checkPassword)) {
            confirmNewPassword.setError("Password should contain atleast 4 characters!");
            return false;

        }
        else {
            confirmNewPassword.setError(null);
            confirmNewPassword.setErrorEnabled(false);
            return true;
        }

    }
}