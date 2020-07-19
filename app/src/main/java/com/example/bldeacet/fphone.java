package com.example.bldeacet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

public class fphone extends AppCompatActivity {
    TextInputLayout fphone;
    ScrollView scrollView2;
    String f_phone;
    CountryCodePicker countryCodePicker2;
    ImageView back5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_fphone);

        back5=findViewById(R.id.back5);
        back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fphone.super.onBackPressed();
            }
        });

        scrollView2 = findViewById(R.id.fsignup_2nd_screen_scroll_view);
        countryCodePicker2 = findViewById(R.id.county_code_picker2);
        fphone = findViewById(R.id.fphone);


    }

    public void fcallNextSignupScreen2(View view) {
        if (!validatephone()) {
            return;
        } else {

            String _getUserEnteredPhoneNumber = fphone.getEditText().getText().toString().trim();
            String _phoneNo = "+" + countryCodePicker2.getFullNumber() + _getUserEnteredPhoneNumber;

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userNameRef2 = rootRef.child("Student");
            Query query2 = userNameRef2.orderByChild("phoneNo").equalTo(_phoneNo);

            ValueEventListener eventListener1 = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {


                        String _getUserEnteredPhoneNumber = fphone.getEditText().getText().toString().trim();
                        String _phoneNo = "+" + countryCodePicker2.getFullNumber() + _getUserEnteredPhoneNumber;
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference userNameRef1 = rootRef.child("Faculty");
                        Query query1 = userNameRef1.orderByChild("fphoneNo").equalTo(_phoneNo);


                        ValueEventListener eventListener2 = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                if (!dataSnapshot.exists()) {
                                    //create new user
                                    String _name = getIntent().getStringExtra("fname");
                                    String _email = getIntent().getStringExtra("femail");
                                    String _password = getIntent().getStringExtra("fpassword");
                                    String _branch = getIntent().getStringExtra("fbranch");


                                    String _getUserEnteredPhoneNumber = fphone.getEditText().getText().toString().trim();
                                    String _phoneNo = "+" + countryCodePicker2.getFullNumber() + _getUserEnteredPhoneNumber;

                                    Intent verify = new Intent(fphone.this, fverification.class);
                                    verify.putExtra("fname", _name);
                                    verify.putExtra("femail", _email);
                                    verify.putExtra("fpassword", _password);
                                    verify.putExtra("fphone", _phoneNo);
                                    verify.putExtra("fbranch", _branch);
                                    startActivity(verify);
                                } else {
                                    Toast.makeText(fphone.this, "Account already exists!", Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        query1.addListenerForSingleValueEvent(eventListener2);
                    } else {
                        Toast.makeText(fphone.this, "Account already exists!", Toast.LENGTH_SHORT).show();


                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            query2.addListenerForSingleValueEvent(eventListener1);
        }
    }

    private boolean validatephone() {
        String val = fphone.getEditText().getText().toString();
        String checkphone = ".{10,10}";
        if (val.isEmpty()) {
            fphone.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkphone)) {
            fphone.setError("Invalid phone number!");
            return false;
        } else {
            fphone.setError(null);
            fphone.setErrorEnabled(false);
            return true;
        }
    }

}
