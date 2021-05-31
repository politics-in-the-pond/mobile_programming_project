package com.example.mpteam;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import java.lang.ClassCastException;

public class MainActivity extends FragmentActivity {
    public enum MainState {
        START,SUCCESS,DURING_AFTER,DURING_BEFORE
    }
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int NUM_PAGES = 3;
    public MainState state = MainState.START;
    BottomNavigationView bottomNavigationView;
    BoxFragment boxFragment;
    DiaryFragment diaryFragment;
    DiaryFragment2 diaryFragment2;
    DiaryFragment3 diaryFragment3;
    MyPageFragment myPageFragment;
    ArrayList<Fragment> fragment_list;
    int[] menu_item_list = {R.id.mypage, R.id.diary, R.id.box};
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        boxFragment = new BoxFragment();
        diaryFragment = new DiaryFragment();
        diaryFragment2 = new DiaryFragment2();
        diaryFragment3 = new DiaryFragment3();
        myPageFragment = new MyPageFragment();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 안드로이드 앱 내부에서 공유할 유저 토큰을 담을 저장소
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String token = user.getUid();
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        pref.edit().putString("userToken", token);
        Log.d("MainToken", "This is token: " + token);

        Intent intent = getIntent();
        if (getIntent() != null) {
            state = MainState.values()[intent.getIntExtra("state", 0)];
            Log.v("state",Integer.toString(intent.getIntExtra("state",0)));
            viewPager = findViewById(R.id.pager);
            setFragmentlist();
            pagerAdapter.notifyDataSetChanged();
        }

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
public void setFragmentlist()
{
    fragment_list = new ArrayList<>();
    if(state==MainState.START) {
        fragment_list.add(myPageFragment);
        fragment_list.add(diaryFragment);
        fragment_list.add(boxFragment);
    }
    else if(state==MainState.DURING_BEFORE)
    {
        fragment_list.add(myPageFragment);
        fragment_list.add(diaryFragment2);
        fragment_list.add(boxFragment);

    }
    else if(state==MainState.DURING_AFTER)
    {
        fragment_list.add(myPageFragment);
        fragment_list.add(diaryFragment3);
        fragment_list.add(boxFragment);
    }
    else if(state==MainState.SUCCESS)
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
}
    public void onStart()
    {
        super.onStart();
        viewPager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);
    }
    public void onBackPressed() {
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
             if (resultCode == RESULT_OK) {
                state=MainState.values()[data.getIntExtra("state",0)];
                setFragmentlist();
                pagerAdapter.notifyDataSetChanged();
                viewPager.setCurrentItem(1);
                Log.v("state222",Integer.toString(data.getIntExtra("state",0)));
                 UpdateDataInteger("streak", "period", data.getIntExtra("period",-1));
                 UpdateData("streak", "startDay", DateModule.getToday());
             }
             else if(resultCode == RESULT_CANCELED)
             {
                 state=MainState.values()[data.getIntExtra("state",0)];
                 setFragmentlist();
                 pagerAdapter.notifyDataSetChanged();
                 viewPager.setCurrentItem(0);
             }
           } else if (requestCode == 2) {

         }
        }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
                Log.v("taggddd", Integer.toString(position));
                Bundle bundle = new Bundle(9); // 파라미터는 전달할 데이터 개수
                int num = state == MainState.START ? 0 : state == MainState.SUCCESS ? 1 : state == MainState.DURING_BEFORE ? 2 : 3;
                bundle.putInt("state", num); // key , value
                //화면에 보여지는 fragment를 추가하거나 바꿀 수 있는 객체를 만든다.
                fragment_list.get(position).setArguments(bundle);
                return fragment_list.get(position);


        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
    private void UpdateDataInteger(String collection, String field, int content) {
        db = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        final DocumentReference sfDocRef = db.collection(collection).document(uid);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sfDocRef);
                transaction.update(sfDocRef, field, content);
                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("scmsg", "Transaction success!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fmsg", "Transaction failure.", e);
                    }

                });
    }

    private void UpdateData(String collection, String field, String content) {
        db = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        final DocumentReference sfDocRef = db.collection(collection).document(uid);

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sfDocRef);
                transaction.update(sfDocRef, field, content);
                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("scmsg", "Transaction success!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fmsg", "Transaction failure.", e);
                    }

                });
    }

}