package com.example.trail;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {
    EditText  otpInput;
    Button sendOtpBtn, login;
    String phoneInput;
    String verificationCode;
    Long timeoutSeconds = 60L;
    PhoneAuthProvider.ForceResendingToken  resendingToken;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        sendOtpBtn = findViewById(R.id.resend);
        otpInput = findViewById(R.id.otp);
        login = findViewById(R.id.login);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        sendOtp(phoneInput, false);
        login.setOnClickListener(v -> {
            String enteredOtp = otpInput.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, enteredOtp);
            signIn(credential);
        });
        sendOtpBtn.setOnClickListener((v)->{
            sendOtp(phoneInput,true);
        });
        Intent intent = getIntent();
        phoneInput = intent.getStringExtra("phone");
        Log.d("phonee", phoneInput);

// Check if phoneInput is not null or empty before calling sendOtp
        if (phoneInput != null && !phoneInput.isEmpty()) {
            sendOtp(phoneInput, false);
        } else {
            // Handle the case where phoneInput is null or empty
            Log.e("Error", "Invalid or empty phone number");
            // You may want to show a Toast or perform some other error handling
        }

    }
    void sendOtp(String phoneInput,boolean isResend) {
        startResendTimer();
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneInput)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // (optional) Activity for callback binding
                // If no activity is passed, reCAPTCHA verification can not be used.
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(otp.this, "Otp Failed", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationCode = s;
                        resendingToken = forceResendingToken;
                        Toast.makeText(otp.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    }
                });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }
    void signIn(PhoneAuthCredential phoneAuthCredential){
        //login and go to next activity
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(otp.this,MainActivity.class);
                    intent.putExtra("phone",phoneInput);
                    startActivity(intent);
                }else{
                    Toast.makeText(otp.this, "OTP verification failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void startResendTimer(){
        sendOtpBtn.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                sendOtpBtn.setText("Resend OTP in "+timeoutSeconds +" seconds");
                if(timeoutSeconds<=0){
                    timeoutSeconds =60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        sendOtpBtn.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }
}