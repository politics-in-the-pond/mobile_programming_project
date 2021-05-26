package com.example.mpteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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