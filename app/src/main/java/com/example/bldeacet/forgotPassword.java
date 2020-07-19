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
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class forgotPassword extends AppCompatActivity {
    TextInputLayout forgotphone;
    ScrollView scrollView;
    String s_phone;
    ImageView back8;
    ProgressBar progressBar3;

    CountryCodePicker countryCodePicker3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_forgot_password);

        back8=findViewById(R.id.back8);
        progressBar3=findViewById(R.id.progressBar3);
        back8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword.super.onBackPressed();
            }
        });

        //hooks


        countryCodePicker3=findViewById(R.id.county_code_picker3);
        forgotphone=findViewById(R.id.forgotphone);
    }



    public void callNextForgotScreen(View view){
        if(!validatephone()){
            return;
        }

        else {
            progressBar3.setVisibility(View.VISIBLE);
            String _getUserEnteredPhoneNumber = forgotphone.getEditText().getText().toString().trim();
            String _phoneNo = "+" + countryCodePicker3.getFullNumber() + _getUserEnteredPhoneNumber;

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

            DatabaseReference userNameRef2 = rootRef.child("Faculty");

            Query query2 = userNameRef2.orderByChild("fphoneNo").equalTo(_phoneNo);

            ValueEventListener eventListener1 = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {


                        String _getUserEnteredPhoneNumber = forgotphone.getEditText().getText().toString().trim();
                        String _phoneNo = "+" + countryCodePicker3.getFullNumber() + _getUserEnteredPhoneNumber;

                        Intent verify = new Intent(forgotPassword.this, verification.class);
                        verify.putExtra("phone", _phoneNo);
                        verify.putExtra("forgotPassword","updateData");
                        startActivity(verify);
                        progressBar3.setVisibility(View.GONE);
                    }
                    else{


                        String _getUserEnteredPhoneNumber = forgotphone.getEditText().getText().toString().trim();
                        String _phoneNo = "+" + countryCodePicker3.getFullNumber() + _getUserEnteredPhoneNumber;
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference userNameRef1 = rootRef.child("Student");
                        Query query1 = userNameRef1.orderByChild("phoneNo").equalTo(_phoneNo);

                        ValueEventListener eventListener2 = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                if (dataSnapshot.exists()) {


                                    String _getUserEnteredPhoneNumber = forgotphone.getEditText().getText().toString().trim();
                                    String _phoneNo = "+" + countryCodePicker3.getFullNumber() + _getUserEnteredPhoneNumber;

                                    Intent verify = new Intent(forgotPassword.this, verification.class);
                                    verify.putExtra("phone", _phoneNo);
                                    verify.putExtra("forgotPassword","updateData");
                                    startActivity(verify);
                                    progressBar3.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(forgotPassword.this, "Account does not exist!", Toast.LENGTH_SHORT).show();
                                    progressBar3.setVisibility(View.GONE);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };

                        query1.addListenerForSingleValueEvent(eventListener2);

                    }



                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            query2.addListenerForSingleValueEvent(eventListener1);




        }

    }





    private boolean validatephone(){
        String val=forgotphone.getEditText().getText().toString();
        String checkphone=".{10,10}";
        if(val.isEmpty()){
            forgotphone.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkphone)){
            forgotphone.setError("Invalid phone number!");
            return false;
        }

        else {
            forgotphone.setError(null);
            forgotphone.setErrorEnabled(false);
            return true;
        }
    }

}