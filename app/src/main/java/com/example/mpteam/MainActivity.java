package com.example.mpteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

        private static final String TAG = "MainActivity";

        private FirebaseAuth firebaseAuth;

        Button memberWithdrawal,logOut;
        ProgressDialog progressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memberWithdrawal =(Button) findViewById(R.id.memberWithdrawal);
        logOut = (Button) findViewById(R.id.logOut);
        progressDialog = new ProgressDialog(this); //conncect Function

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        memberWithdrawal.setOnClickListener(this);
        logOut.setOnClickListener(this);

    }
    private void Logout(){

    }
    private void deleteUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog.setMessage("Deleting User. please wait a moment please...");
        progressDialog.show();

        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Your account has been deleted.", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.memberWithdrawal:
                deleteUser();
                break;
            case R.id.logOut:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }
}
