package com.example.ado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail, textInputPassword,
            textInputConfirmPassword;
    private Button loginSignUpButton;
    private TextView toggleLoginSignUpTextView;
    private FirebaseAuth auth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabaseReference;

    private String userId;

    boolean isLoginModActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_sign_in);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        loginSignUpButton = findViewById(R.id.loginSignUpButton);
        toggleLoginSignUpTextView = findViewById(R.id.toggleLoginSignUpTextView);

        isLoginModActive = false;

        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabaseReference = firebaseDatabase.getReference().child("user");


    }


    public void signUpLogIn(View view) {

        String email;
        String password;


        email = textInputEmail.getEditText().getText().toString().trim();
        password = textInputPassword.getEditText().getText().toString().trim();



        if (!isLoginModActive) {

            if (!validateEmail() | !validatePassword() | !validateConfirmPassword()) {
                return;
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("tag", "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                userId = user.getUid();
                                userDatabaseReference.setValue(userId);
                                startActivity(new Intent(SignInActivity.this,
                                        DoActivity.class));
                                finish();
                            } else {
                                Log.w("tag", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );


        } else if (isLoginModActive) {
            if (!validateEmail() | !validatePassword()) {
                return;
            }
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("tag", "signInWithEmail:success");
                                        FirebaseUser user = auth.getCurrentUser();
                                        startActivity(new Intent(
                                                SignInActivity.this,
                                                DoActivity.class
                                        ));
                                        finish();

                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("tag", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });

        }

    }

    public void toggleSignUpLogIn(View view) {

        if (isLoginModActive) {
            isLoginModActive = false;
            loginSignUpButton.setText("Sign Up");
            toggleLoginSignUpTextView.setText("Or Log In");
            textInputConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            isLoginModActive = true;
            loginSignUpButton.setText("Log In");
            toggleLoginSignUpTextView.setText("Or Sign Up");
            textInputConfirmPassword.setVisibility(View.GONE);
        }

    }

    private boolean validateEmail() {

        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Please input your email");
            return false;
        } else {
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean validatePassword() {

        String passwordInput = textInputPassword.getEditText().getText()
                .toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Please input your password");
            return false;
        } else if (passwordInput.length() < 7) {
            textInputPassword.setError("Password length have to be more than 6");
            return false;
        } else {

            return true;
        }


    }

    private boolean validateConfirmPassword() {

        String passwordInput = textInputPassword.getEditText().getText()
                .toString().trim();
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText()
                .toString().trim();

        if (!passwordInput.equals(confirmPasswordInput)) {
            textInputConfirmPassword.setError("Passwords have to match");
            return false;
        } else {

            return true;
        }

    }

}
