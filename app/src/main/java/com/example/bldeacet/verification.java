package com.example.bldeacet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class verification extends AppCompatActivity {
    TextView otp;
    PinView pinFromUser;
    String codeBySystem;
    TextInputLayout sphone;
    ScrollView scrollView;
    String s_phone;
    CountryCodePicker countryCodePicker;
    ImageView back6;
    String name, email, password, phoneNo, roll, usn, branch, sem, forgotPassword;
    ProgressBar progressBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_verification);

        progressBar4=findViewById(R.id.progressBar4);
        back6 = findViewById(R.id.back6);
        back6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification.super.onBackPressed();
            }
        });

        otp = (TextView) (findViewById(R.id.otp));
        Intent i = getIntent();
        String _phone = i.getStringExtra("phone");
// Now set this value to EditText
        otp.setText(_phone);

        //hooks
        pinFromUser = findViewById(R.id.pin_view);
        countryCodePicker = findViewById(R.id.county_code_picker);
        sphone = findViewById(R.id.phone);


        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        roll = getIntent().getStringExtra("roll");
        usn = getIntent().getStringExtra("usn");
        branch = getIntent().getStringExtra("branch");
        sem = getIntent().getStringExtra("sem");
        phoneNo = getIntent().getStringExtra("phone");
        //forgotphone = getIntent().getStringExtra("forgotphone");
        forgotPassword = getIntent().getStringExtra("forgotPassword");


        sendVerificationCodeToUser(phoneNo);

    }

    private void sendVerificationCodeToUser(String _phone) {
        progressBar4.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                _phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        progressBar4.setVisibility(View.GONE);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    progressBar4.setVisibility(View.VISIBLE);
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                    progressBar4.setVisibility(View.GONE);
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    progressBar4.setVisibility(View.VISIBLE);
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                    progressBar4.setVisibility(View.GONE);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    progressBar4.setVisibility(View.VISIBLE);
                    Toast.makeText(verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    progressBar4.setVisibility(View.GONE);

                }
            };

    private void verifyCode(String code) {
        progressBar4.setVisibility(View.GONE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        progressBar4.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sphone = findViewById(R.id.phone);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(verification.this, "Verification Completed!", Toast.LENGTH_SHORT).show();
                            progressBar4.setVisibility(View.GONE);

                            if (forgotPassword.equals("updateData")) {

                                updateOldUserData();
                            } else {
                                progressBar4.setVisibility(View.VISIBLE);
                                storeNewStudentData();
                                Intent studentDashboard = new Intent(verification.this, studentDashboard.class);
                            /*studentDashboard.putExtra ( "name",_name);
                            studentDashboard.putExtra ( "email",_email);
                            studentDashboard.putExtra ( "password",_password);
                            studentDashboard.putExtra ( "phone",_phoneNo);
                            studentDashboard.putExtra ( "roll",_roll);
                            studentDashboard.putExtra ( "usn",_usn);
                            studentDashboard.putExtra ( "branch",_branch);
                            studentDashboard.putExtra ( "sem",_sem);*/
                                startActivity(studentDashboard);
                                finish();
                                progressBar4.setVisibility(View.GONE);
                            }


                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(verification.this, "Invalid Verification! Try later.", Toast.LENGTH_SHORT).show();
                                progressBar4.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    private void updateOldUserData() {
        progressBar4.setVisibility(View.VISIBLE);
        Intent setNewPassword=new Intent(getApplicationContext(), setNewPassword.class);
        setNewPassword.putExtra("forgotphone",phoneNo);
        startActivity(setNewPassword);
        finish();
        progressBar4.setVisibility(View.GONE);
    }

    private void storeNewStudentData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Student");

        UserHelperClass addNewStudent = new UserHelperClass(name, email, password, phoneNo, roll, usn, branch, sem);
        reference.child(phoneNo).setValue(addNewStudent);


    }


    public void callNextScreenFromOTP(View view) {

        String code = pinFromUser.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}