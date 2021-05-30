package com.example.mpteam;

import android.app.Activity;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.example.mpteam.modules.DateModule;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    public enum MainState {
        START,SUCCESS,DURING_AFTER,DURING_BEFORE
    }
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int NUM_PAGES = 3;
    MainState state = MainState.START;
    BottomNavigationView bottomNavigationView;
    BoxFragment boxFragment;
    DiaryFragment diaryFragment;
    DiaryFragment2 diaryFragment2;
    MyPageFragment myPageFragment;
    ArrayList<Fragment> fragment_list;
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
        diaryFragment2 = new DiaryFragment2();
        myPageFragment = new MyPageFragment();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 안드로이드 앱 내부에서 공유할 유저 토큰을 담을 저장소
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String token = user.getUid();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        pref.edit().putString("userToken", token);
        Log.d("MainToken", "This is token: " + token);

        fragment_list = new ArrayList<>();
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);

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
    public void onResume()
    {
        super.onResume();
        ApplyState();


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
    private void SetFragment(MainState st)
    {
        fragment_list = new ArrayList<>();
        if(st==MainState.START) {
            fragment_list.add(myPageFragment);
            fragment_list.add(diaryFragment);
            fragment_list.add(boxFragment);

        }
        else if(st==MainState.DURING_BEFORE)
        {
            fragment_list.add(myPageFragment);
            fragment_list.add(diaryFragment2);
            fragment_list.add(boxFragment);

        }
        else if(st==MainState.DURING_AFTER)
        {
            fragment_list.add(myPageFragment);
            fragment_list.add(myPageFragment);
            fragment_list.add(boxFragment);
        }
        else if(st==MainState.SUCCESS)
        {
            fragment_list.add(myPageFragment);
            fragment_list.add(diaryFragment2);
            fragment_list.add(boxFragment);
        }
        else
        {
            fragment_list.add(myPageFragment);
            fragment_list.add(diaryFragment);
            fragment_list.add(boxFragment);
        }
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
    }

    private void ApplyState()
    {
        String today = DateModule.getToday();
        db.collection("streak").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                String lastDay = document.get("lastDay").toString();
                String startDay = document.get("startDay").toString();
                int period = Integer.parseInt(document.get("period").toString());
                if(period==-1)
                {
                    SetFragment(MainState.START) ;

                }
                else if(period==0)
                {
                    SetFragment(MainState.SUCCESS) ;

                }
                else
                {
                    if(DateModule.compareDay(today,lastDay)==0){ //오늘 썼을 때
                        SetFragment(MainState.DURING_AFTER) ;
                    }
                    else
                    {
                        SetFragment(MainState.DURING_BEFORE) ;
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
              if (resultCode == RESULT_OK) {
                    ApplyState();
             }
           } else if (requestCode == 1) {

         }
        }

}