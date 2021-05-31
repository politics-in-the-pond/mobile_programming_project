package com.example.mpteam.modules;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mpteam.data.DiaryStreak;
import com.example.mpteam.data.PostData;
import com.example.mpteam.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataDB {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setUserData(UserData user) {
        db.collection("users").document(user.getUserId()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void setPostData(PostData post) {
        db.collection("posts").document(post.getUserId()).collection("posts").add(post)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d("TAG", "업로드 완료");
                    }
                });
    }

    public void updateDiaryStreak(Context context) {
        db.collection("streak").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                DiaryStreak ds = new DiaryStreak(user.getUid(), document.get("startDay").toString(), document.get("lastDay").toString(), Integer.parseInt(document.get("gauge").toString()), Integer.parseInt(document.get("period").toString()));
                if (DateModule.compareDay(document.get("lastDay").toString(), DateModule.getToday()) > 1) { //하루이상 안썼을 때
                    Log.d("DataDB", "Game Over"); //게임오버 추가
                }
                if (DateModule.compareDay(ds.getLastDay(), DateModule.getToday()) == 0) {
                    Toast.makeText(context, "이미 오늘 일기를 썼습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("DataDB", "저런 오늘은 일기를 쓰셨네요!");
                } else{
                    ds.setGauge(ds.getGauge() + 1);
                }
                ds.setLastDay(DateModule.getToday());

                db.collection("streak").document(user.getUid()).set(ds).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });
    }

    public void useCoin(Context context) {
        db.collection("streak").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                DiaryStreak ds = new DiaryStreak(document.get("userId").toString(), document.get("startDay").toString(),  document.get("lastDay").toString(), Integer.parseInt(document.get("gauge").toString()), Integer.parseInt(document.get("period").toString()));
                if (DateModule.compareDay(ds.getLastDay(), DateModule.getToday()) == 0) {
                    Toast.makeText(context, "이미 오늘 일기를 썼습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("DataDB", "저런 오늘은 일기를 쓰셨네요!");
                    return;
                }
                if (Integer.parseInt(document.get("gauge").toString()) >= 7) {
                    ds.setGauge(ds.getGauge() - 7);
                    ds.setLastDay(DateModule.getToday());
                    ds.setPeriod(ds.getPeriod()-1);
                    db.collection("streak").document(user.getUid()).set(ds).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "코인을 사용했습니다.", Toast.LENGTH_SHORT).show();
                            Log.d("DataDB", "코인사용완료");
                        }
                    });
                } else {
                    Toast.makeText(context, "코인이 부족합니다", Toast.LENGTH_SHORT).show();
                    Log.d("DataDB", "저런 코인이 부족하네요! 코인 개수 : " + Integer.toString(ds.getGauge() / 7) + " 남은 게이지 : " + Integer.toString(ds.getGauge() % 7));
                }
            }
        });
    }

    public void updateStart() {
        db.collection("streak").document(user.getUid()).update("startDay",DateModule.getToday());
    }

    public void clearStreak() {
        DiaryStreak ds = new DiaryStreak(user.getUid(), "", "", 0, -1);
        db.collection("streak").document(user.getUid()).set(ds);
    }

/*    테스트용 코드
        DataDB db2 = new DataDB();
        ArrayList<String> test = new ArrayList<String>();
        test.add("asdsa");
        db2.setPostData(new PostData(user.getUid(),"제목","내용","x월x일xx시xx분",3.14,3.14,false,test, 3));*/

}
