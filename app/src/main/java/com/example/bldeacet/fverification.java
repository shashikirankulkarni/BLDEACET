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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class fverification extends AppCompatActivity {
    TextView otp2;
    PinView fpinFromUser;
    String fcodeBySystem;
    TextInputLayout fphone;
    ScrollView fscrollView;
    String f_phone;
    CountryCodePicker countryCodePicker2;
    ImageView back7;
    String fname,femail,fpassword,fphoneNo,fbranch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_fverification);

        back7=findViewById(R.id.back7);
        back7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fverification.super.onBackPressed();
            }
        });

        otp2=(TextView)(findViewById(R.id.otp2));
        Intent i = getIntent();
        String _phone = i.getStringExtra ( "fphone");
        // Now set this value to EditText
        otp2.setText (_phone);

        //hooks
        fpinFromUser=findViewById(R.id.fpin_view);
        countryCodePicker2=findViewById(R.id.county_code_picker2);
        fphone=findViewById(R.id.fphone);


        fname=getIntent().getStringExtra("fname");
        femail=getIntent().getStringExtra("femail");
        fpassword=getIntent().getStringExtra("fpassword");
        fbranch=getIntent().getStringExtra("fbranch");
        fphoneNo=getIntent().getStringExtra("fphone");



        sendVerificationCodeToUser(fphoneNo);

    }


    private void sendVerificationCodeToUser(String _phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                _phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    fcodeBySystem=s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code=phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        fpinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(fverification.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(fcodeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fphone=findViewById(R.id.phone);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(fverification.this,"Verification Completed!",Toast.LENGTH_SHORT).show();

                            storeNewFacultyData();






                            Intent facultyDashboard=new Intent(fverification.this,facultyDashboard.class);
                            /*studentDashboard.putExtra ( "name",_name);
                            studentDashboard.putExtra ( "email",_email);
                            studentDashboard.putExtra ( "password",_password);
                            studentDashboard.putExtra ( "phone",_phoneNo);
                            studentDashboard.putExtra ( "roll",_roll);
                            studentDashboard.putExtra ( "usn",_usn);
                            studentDashboard.putExtra ( "branch",_branch);
                            studentDashboard.putExtra ( "sem",_sem);*/
                            startActivity(facultyDashboard);

                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(fverification.this,"Invalid Verification! Try later.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void storeNewFacultyData() {
        FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
        DatabaseReference reference=rootNode.getReference("Faculty");

        UserHelperClass2 addNewFaculty=new UserHelperClass2(fname,femail,fpassword,fphoneNo,fbranch);
        reference.child(fphoneNo).setValue(addNewFaculty);



    }


    public void fcallNextScreenFromOTP(View view) {

        String code=fpinFromUser.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }

}