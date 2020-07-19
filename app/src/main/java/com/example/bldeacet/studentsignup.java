package com.example.bldeacet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class studentsignup extends AppCompatActivity {
private TextInputLayout sbranch,ssem,sname,semail,spassword,sroll,usn;
private AutoCompleteTextView branchdropdown,semdropdown;
private Button next;
ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_studentsignup);

        back=findViewById(R.id.back2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentsignup.super.onBackPressed();
            }
        });

        sbranch=findViewById(R.id.branchlayout);
        branchdropdown=findViewById(R.id.branchdropdown);
        String[] items= new String[]{
          "CS",
          "EC",
          "CV",
           "ME"
        };

        ArrayAdapter<String>adapter=new ArrayAdapter<>(
                studentsignup.this,
                R.layout.dropdown_item,
                items
        );

        branchdropdown.setAdapter(adapter);
        branchdropdown.setInputType(0);
        branchdropdown.setTextColor(Color.BLACK);



        //SEM Dropdown


        ssem=findViewById(R.id.semlayout);
        semdropdown=findViewById(R.id.semdropdown);
        String[] items2= new String[]{
                "6th Sem A-div",
                "6th Sem B-div"
        };

        ArrayAdapter<String>adapter2=new ArrayAdapter<>(
                studentsignup.this,
                R.layout.dropdown_item,
                items2
        );

        semdropdown.setAdapter(adapter2);
        semdropdown.setInputType(0);
        semdropdown.setTextColor(Color.BLACK);


        //Button
        /*next=(Button)(findViewById(R.id.next2));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phone=new Intent(studentsignup.this,sphone.class);
                startActivity(phone);
            }
        });*/


        //hooks for getting data

        sname=findViewById(R.id.sname);
        semail=findViewById(R.id.semail);
        spassword=findViewById(R.id.spassword);
        sroll=findViewById(R.id.sroll);
        usn=findViewById(R.id.usn);




    }

    public void callNextSignupScreen(View view){
        if(!validateName()| !validateEmail() | !validatePassword() | !validateRoll() |
        !validateUSN() | !validateBranch() | !validateSem()){
            return;
        }

        else {
            Intent phone=new Intent(studentsignup.this,sphone.class);
            phone.putExtra ( "sname",sname.getEditText().getText().toString());
            phone.putExtra ( "semail",semail.getEditText().getText().toString());
            phone.putExtra ( "spassword",spassword.getEditText().getText().toString());
            phone.putExtra ( "sroll",sroll.getEditText().getText().toString());
            phone.putExtra ( "usn",usn.getEditText().getText().toString());
            phone.putExtra ( "sbranch",sbranch.getEditText().getText().toString());
            phone.putExtra ( "ssem",ssem.getEditText().getText().toString());
            startActivity(phone);
        }


    }

    //Validation functions
    private boolean validateName(){
        String val=sname.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            sname.setError("Field can not be empty");
            return false;
        }

        else {
            sname.setError(null);
            sname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val=semail.getEditText().getText().toString().trim();
        String checkspace="\\A\\w{1,50}\\z";
        String checkemail="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            semail.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkemail)){
            semail.setError("Invalid Email!");
            return false;
        }

        else {
            semail.setError(null);
            semail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val=spassword.getEditText().getText().toString().trim();
        String checkPassword="(?=\\S+$)" +
                                ".{4,}" +
                                    "$";
        if(val.isEmpty()){
            spassword.setError("Field can not be empty");
            return false;
        }
        else if(!val.matches(checkPassword)){
            spassword.setError("Password should contain atleast 4 characters!");
            return false;

        }

        else {
            spassword.setError(null);
            spassword.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateRoll(){
        String val=sroll.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            sroll.setError("Field can not be empty");
            return false;
        }

        else {
            sroll.setError(null);
            sroll.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateUSN(){
        String val=usn.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            usn.setError("Field can not be empty");
            return false;
        }

        else {
            usn.setError(null);
            usn.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateSem(){
        String val=ssem.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            ssem.setError("Field can not be empty");
            return false;
        }

        else {
            ssem.setError(null);
            ssem.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateBranch(){
        String val=sbranch.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            sbranch.setError("Field can not be empty");
            return false;
        }

        else {
            sbranch.setError(null);
            sbranch.setErrorEnabled(false);
            return true;
        }
    }



}