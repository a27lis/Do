package com.example.ado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SplashScreenActivity.this,
                    DoActivity.class));
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    }   catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(
                                SplashScreenActivity.this, SignInActivity.class));
                    }
                }
            };
            thread.start();
        }




    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
