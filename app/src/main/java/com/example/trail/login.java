package com.example.trail;
import android.content.Intent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;
import com.hbb20.CountryCodePicker;

import java.util.Arrays;
import java.util.List;

public class login extends AppCompatActivity {
        EditText phoneInput;
        Button sendOtpBtn, gauth;
        CountryCodePicker countryCodePicker;


    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult o) {
                    onSignInResult(o);
                }
            }
    );




    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            countryCodePicker = findViewById(R.id.login_countrycode);
            phoneInput = findViewById(R.id.editTextPhone);
            sendOtpBtn = findViewById(R.id.button9);
            gauth = findViewById(R.id.loginbtn);

            gauth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.PhoneBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.FacebookBuilder().build(),
                            new AuthUI.IdpConfig.TwitterBuilder().build());

                    Intent signInIntent = AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build();
                    signInLauncher.launch(signInIntent);
                }
            });

//            countryCodePicker.registerCarrierNumberEditText(phoneInput);
//            sendOtpBtn.setOnClickListener(v -> {
//                if (!countryCodePicker.isValidFullNumber()) {
//                    phoneInput.setError("Phone number not valid");
//                } else {
//                    String phoneNumber = countryCodePicker.getFullNumberWithPlus();
//                    Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(login.this, otp.class);
//                    intent.putExtra("phone", phoneNumber);
//                    startActivity(intent);
//                }
//            });
        }


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            Toast.makeText(this, " User came here brooo", Toast.LENGTH_SHORT).show();
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    }