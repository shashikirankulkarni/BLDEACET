package com.example.bldeacet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;


public class sphone extends AppCompatActivity {
TextInputLayout sphone;
ScrollView scrollView;
String s_phone;
ImageView back4;

    CountryCodePicker countryCodePicker;

Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sphone);

        back4=findViewById(R.id.back4);
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sphone.super.onBackPressed();
            }
        });

        //next=(Button)(findViewById(R.id.next3));

        /*next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verify = new Intent ( sphone.this, verification.class );
                verify.putExtra ( "phone", phone.getText().toString() );
                startActivity(verify);
            }
        });*/

        //hooks

        scrollView=findViewById(R.id.signup_2nd_screen_scroll_view);
        countryCodePicker=findViewById(R.id.county_code_picker);
        sphone=findViewById(R.id.phone);





    }




    public void callNextSignupScreen2(View view){


        if(!validatephone()){
            return;
        }

        else {
            String _getUserEnteredPhoneNumber = sphone.getEditText().getText().toString().trim();
            String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

            DatabaseReference userNameRef2 = rootRef.child("Faculty");

            Query query2 = userNameRef2.orderByChild("fphoneNo").equalTo(_phoneNo);

            ValueEventListener eventListener1 = new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {

                        //do nothing
                        String _getUserEnteredPhoneNumber = sphone.getEditText().getText().toString().trim();
                        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference userNameRef1 = rootRef.child("Student");
                        Query query1 = userNameRef1.orderByChild("phoneNo").equalTo(_phoneNo);

                        ValueEventListener eventListener2 = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                if (!dataSnapshot.exists()) {
                                    //create new user
                                    String _name = getIntent().getStringExtra("sname");
                                    String _email = getIntent().getStringExtra("semail");
                                    String _password = getIntent().getStringExtra("spassword");
                                    String _roll = getIntent().getStringExtra("sroll");
                                    String _usn = getIntent().getStringExtra("usn");
                                    String _branch = getIntent().getStringExtra("sbranch");
                                    String _sem = getIntent().getStringExtra("ssem");

                                    String _getUserEnteredPhoneNumber = sphone.getEditText().getText().toString().trim();
                                    String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

                                    Intent verify = new Intent(sphone.this, verification.class);
                                    verify.putExtra("name", _name);
                                    verify.putExtra("email", _email);
                                    verify.putExtra("password", _password);
                                    verify.putExtra("phone", _phoneNo);
                                    verify.putExtra("roll", _roll);
                                    verify.putExtra("usn", _usn);
                                    verify.putExtra("branch", _branch);
                                    verify.putExtra("sem", _sem);
                                    startActivity(verify);
                                } else {
                                    Toast.makeText(sphone.this, "Account already exists!", Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };

                        query1.addListenerForSingleValueEvent(eventListener2);


                    } else {
                        Toast.makeText(sphone.this, "Account already exists!", Toast.LENGTH_SHORT).show();




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
        String val=sphone.getEditText().getText().toString();
        String checkphone=".{10,10}";
        if(val.isEmpty()){
            sphone.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkphone)){
            sphone.setError("Invalid phone number!");
            return false;
        }

        else {
            sphone.setError(null);
            sphone.setErrorEnabled(false);
            return true;
        }
    }
}