package com.example.mpteam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpteam.modules.AutoLoginProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    AutoLoginProvider autoLoginProvider;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        autoLoginProvider = new AutoLoginProvider();

        if (autoLoginProvider.AutoLoginChecker()) {
            String[] emailpw = autoLoginProvider.AutoLoginReader();
            firebaseAuth.signInWithEmailAndPassword(emailpw[0], emailpw[1])
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        }
                    });
        } else {
            startLoading();
        }
    }

    private void startLoading() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500); //1500ms(1.5s) delay of the splash screen
    }
}