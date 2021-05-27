package com.example.mpteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private static final int NUM_PAGES = 3;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    BottomNavigationView bottomNavigationView;
    BoxFragment boxFragment;
    DiaryFragment diaryFragment;
    MyPageFragment myPageFragment;
    ArrayList<Fragment> fragment_list=new ArrayList<>();
    int [] menu_item_list={R.id.mypage,R.id.diary,R.id.box};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        boxFragment = new BoxFragment();
        diaryFragment = new DiaryFragment();
        myPageFragment = new MyPageFragment();
        fragment_list.add(myPageFragment);
        fragment_list.add(diaryFragment);
        fragment_list.add(boxFragment);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mypage:{
                        viewPager.setCurrentItem(0);
                        return true;
                    } case R.id.diary:{
                        viewPager.setCurrentItem(1);
                        return true;
                    } case R.id.box:{
                        viewPager.setCurrentItem(2);
                        return true;

                    }default: return false;
                }
            }
        });
        viewPager.registerOnPageChangeCallback( new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pos) {
                super.onPageSelected(pos);
                bottomNavigationView.getMenu().getItem(pos).setChecked(true);
            }
        });
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
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            return fragment_list.get(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}