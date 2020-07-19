package com.example.bldeacet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SignUp extends AppCompatActivity {
Button next;
ImageView back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_up);

        back1=findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.super.onBackPressed();
            }
        });
        next=(Button)(findViewById(R.id.next));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                RadioButton sRadioButton = (RadioButton) findViewById(R.id.studentbtn);
                Boolean sRadioButtonState = sRadioButton.isChecked();
                RadioButton fRadioButton = (RadioButton) findViewById(R.id.facultybtn);
                Boolean fRadioButtonState = fRadioButton.isChecked();

                if(sRadioButtonState){
                    Intent studentSignup=new Intent(SignUp.this,studentsignup.class);
                    startActivity(studentSignup);
                }

                if(fRadioButtonState){
                    Intent facultySignup=new Intent(SignUp.this,facultysignup.class);
                    startActivity(facultySignup);
                }

            }
        });
    }
}