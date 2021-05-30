package com.example.mpteam;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mpteam.modules.DataDB;
import com.example.mpteam.modules.DateModule;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private static final int NUM_PAGES = 3;
    BottomNavigationView bottomNavigationView;
    BoxFragment boxFragment;
    DiaryFragment diaryFragment;
    MyPageFragment myPageFragment;
    ArrayList<Fragment> fragment_list = new ArrayList<>();
    int[] menu_item_list = {R.id.mypage, R.id.diary, R.id.box};
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

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

        fragment_list.add(myPageFragment);
        fragment_list.add(diaryFragment);
        fragment_list.add(boxFragment);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mypage: {
                        viewPager.setCurrentItem(0);
                        return true;
                    }
                    case R.id.diary: {
                        viewPager.setCurrentItem(1);
                        return true;
                    }
                    case R.id.box: {
                        viewPager.setCurrentItem(2);
                        return true;

                    }
                    default:
                        return false;
                }
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pos) {
                super.onPageSelected(pos);
                bottomNavigationView.getMenu().getItem(pos).setChecked(true);
            }
        });
    }

    public void onResume() {
        super.onResume();
        viewPager.setCurrentItem(1);
    }

    public void onBackPressed() {
        finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {

            return fragment_list.get(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}