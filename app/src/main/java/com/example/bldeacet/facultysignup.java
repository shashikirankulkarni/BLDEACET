package com.example.bldeacet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class facultysignup extends AppCompatActivity {
    private TextInputLayout fbranch,fname,femail,fpassword;
    private AutoCompleteTextView fbranchdropdown;
    private Button next2;
    ImageView back3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_facultysignup);

        back3=findViewById(R.id.back3);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facultysignup.super.onBackPressed();
            }
        });
        fbranch=findViewById(R.id.fbranchlayout);
        fbranchdropdown=findViewById(R.id.fbranchdropdown);
        String[] items= new String[]{
                "CS",
                "EC",
                "CV",
                "ME"
        };


        ArrayAdapter<String>adapter=new ArrayAdapter<>(
                facultysignup.this,
                R.layout.dropdown_item,
                items
        );

        fbranchdropdown.setAdapter(adapter);
        fbranchdropdown.setInputType(0);
        fbranchdropdown.setTextColor(Color.BLACK);


        //hooks for getting data

        fname=findViewById(R.id.fname);
        femail=findViewById(R.id.femail);
        fpassword=findViewById(R.id.fpassword);


    }


    public void fcallNextSignupScreen(View view){
        if(!validateName()| !validateEmail() | !validatePassword()| !validateBranch()){
            return;
        }

        else {
            Intent fphone=new Intent(facultysignup.this,fphone.class);
            fphone.putExtra ( "fname",fname.getEditText().getText().toString());
            fphone.putExtra ( "femail",femail.getEditText().getText().toString());
            fphone.putExtra ( "fpassword",fpassword.getEditText().getText().toString());
            fphone.putExtra ( "fbranch",fbranch.getEditText().getText().toString());

            startActivity(fphone);
        }


    }

    //Validation functions
    private boolean validateName(){
        String val=fname.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            fname.setError("Field can not be empty");
            return false;
        }

        else {
            fname.setError(null);
            fname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val=femail.getEditText().getText().toString().trim();
        String checkspace="\\A\\w{1,50}\\z";
        String checkemail="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            femail.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkemail)){
            femail.setError("Invalid Email!");
            return false;
        }

        else {
            femail.setError(null);
            femail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val=fpassword.getEditText().getText().toString().trim();
        String checkPassword="(?=\\S+$)" +
                ".{4,}" +
                "$";
        if(val.isEmpty()){
            fpassword.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkPassword)){
            fpassword.setError("Password should contain atleast 4 characters!");
            return false;

        }

        else {
            fpassword.setError(null);
            fpassword.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateBranch(){
        String val=fbranch.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            fbranch.setError("Field can not be empty");
            return false;
        }

        else {
            fbranch.setError(null);
            fbranch.setErrorEnabled(false);
            return true;
        }
    }
}