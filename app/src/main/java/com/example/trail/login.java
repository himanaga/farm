package com.example.trail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class login extends AppCompatActivity {
 EditText phoneInput, otpInput;
 Button sendOtpBtn, login;
 CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        countryCodePicker = findViewById(R.id.login_countrycode);
        phoneInput = findViewById(R.id.editTextPhone);
        sendOtpBtn = findViewById(R.id.button9);
        otpInput = findViewById(R.id.editTextNumber);
        login = findViewById(R.id.button8);
    }
    sendOtp(phoneNumber,false);
    PhoneAuthOptions options =
            PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // (optional) Activity for callback binding
                    // If no activity is passed, reCAPTCHA verification can not be used.
                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                    .build();
  PhoneAuthProvider.verifyPhoneNumber(options);


    void signIn(PhoneAuthCredential phoneAuthCredential){
        //login and go to next activity
        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent i = new Intent(this, MainActivity.class);
                    intent.putExtra("phone",phoneNumber);
                        startActivity(i);
                }
                }else{
                    AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
                }
            }
        });

    }