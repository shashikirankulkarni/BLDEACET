package com.example.bldeacet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {
    Button signup, signin;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    ProgressBar progressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);

        signup = (Button) (findViewById(R.id.signup));
        signin = (Button) (findViewById(R.id.signin));
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        progressBar2=findViewById(R.id.progressBar2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, SignUp.class);
                startActivity(signup);
            }
        });




    }

    public void letTheUserLoggedIn(View view) {
        if (!validatePhone()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        checkInternet checkinternet=new checkInternet();
        if(!checkinternet.isConnected(this)){
            Toast.makeText(this,"Please check your internet connection!",Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar2.setVisibility(View.VISIBLE);

        //get data

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        final String _password = password.getEditText().getText().toString().trim();
        final String _completePhoneNumber ="+91"+_phoneNumber;

        //database
        Query checkStudent = FirebaseDatabase.getInstance().getReference("Student")
                .orderByChild("phoneNo").equalTo(_completePhoneNumber);



        checkStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(_completePhoneNumber)
                            .child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _name=dataSnapshot.child(_completePhoneNumber).child("name").getValue(String.class);
                        String _email=dataSnapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _password=dataSnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                        String _phoneNumber=dataSnapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _roll=dataSnapshot.child(_completePhoneNumber).child("roll").getValue(String.class);
                        String _usn=dataSnapshot.child(_completePhoneNumber).child("usn").getValue(String.class);
                        String _branch=dataSnapshot.child(_completePhoneNumber).child("branch").getValue(String.class);
                        String _sem=dataSnapshot.child(_completePhoneNumber).child("sem").getValue(String.class);

                        Intent sdashboard=new Intent(LoginActivity.this,studentDashboard.class);
                        startActivity(sdashboard);

                        Toast.makeText(LoginActivity.this,_name+"\n"+_email+"\n"+_password+"\n"+_phoneNumber+"\n"+_roll+"\n"+_usn+"\n"+_branch+"\n"+_sem,Toast.LENGTH_LONG).show();
                        progressBar2.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                    }
                } else {
                    //Toast.makeText(LoginActivity.this, "Account does not exist!", Toast.LENGTH_SHORT).show();
                    Query checkFaculty = FirebaseDatabase.getInstance().getReference("Faculty")
                            .orderByChild("fphoneNo").equalTo(_completePhoneNumber);
                    checkFaculty.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                phoneNumber.setError(null);
                                phoneNumber.setErrorEnabled(false);

                                String systemPassword = dataSnapshot.child(_completePhoneNumber)
                                        .child("fpassword").getValue(String.class);
                                if (systemPassword.equals(_password)) {
                                    password.setError(null);
                                    password.setErrorEnabled(false);

                                    String _name=dataSnapshot.child(_completePhoneNumber).child("fname").getValue(String.class);
                                    String _email=dataSnapshot.child(_completePhoneNumber).child("femail").getValue(String.class);
                                    String _password=dataSnapshot.child(_completePhoneNumber).child("fpassword").getValue(String.class);
                                    String _phoneNumber=dataSnapshot.child(_completePhoneNumber).child("fphoneNo").getValue(String.class);
                                    String _branch=dataSnapshot.child(_completePhoneNumber).child("fbranch").getValue(String.class);

                                    Intent fdashboard=new Intent(LoginActivity.this,facultyDashboard.class);
                                    startActivity(fdashboard);

                                    Toast.makeText(LoginActivity.this,_name+"\n"+_email+"\n"+_password+"\n"+_phoneNumber+"\n"+_branch,Toast.LENGTH_LONG).show();
                                    progressBar2.setVisibility(View.GONE);

                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                                    //progressBar2.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Account does not exist!", Toast.LENGTH_SHORT).show();
                                progressBar2.setVisibility(View.GONE);
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                    progressBar2.setVisibility(View.GONE);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar2.setVisibility(View.GONE);

            }
        });





    }

    private boolean validatePhone() {
        String val = phoneNumber.getEditText().getText().toString();
        String checkphone = ".{10,10}";
        if (val.isEmpty()) {
            phoneNumber.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkphone)) {
            phoneNumber.setError("Invalid phone number!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String _password = password.getEditText().getText().toString().trim();
        String checkPassword = "(?=\\S+$)" +
                ".{4,}" +
                "$";
        if (_password.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!_password.matches(checkPassword)) {
            password.setError("Password should contain atleast 4 characters!");
            return false;

        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    public void callForgotPassword(View view){
        startActivity(new Intent(getApplicationContext(),forgotPassword.class));

    }


}
