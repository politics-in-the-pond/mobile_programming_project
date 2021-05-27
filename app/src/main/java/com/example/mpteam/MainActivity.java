package com.example.mpteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BoxFragment boxFragment;
    DiaryFragment diaryFragment;
    MyPageFragment myPageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        boxFragment = new BoxFragment();
        diaryFragment = new DiaryFragment();
        myPageFragment = new MyPageFragment();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        // 안드로이드 앱 내부에서 공유할 유저 토큰을 담을 저장소
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String token = user.getUid();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        pref.edit().putString("userToken", token);
        Log.d("MainToken", "This is token: " + token);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,myPageFragment).commitAllowingStateLoss();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mypage:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,myPageFragment).commitAllowingStateLoss();
                        return true;
                    } case R.id.diary:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,diaryFragment).commitAllowingStateLoss();
                        return true;
                    } case R.id.box:{
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,boxFragment).commitAllowingStateLoss();
                        return true;

                    }default: return false;
                }
            }
        });

    }
}