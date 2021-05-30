package com.example.mpteam.modules;

import android.util.Log;

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
import com.google.firestore.v1.Document;

import java.util.Date;

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

    public void updateDiaryStreak(){
        db.collection("streak").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                DiaryStreak ds = new DiaryStreak(user.getUid(),document.get("lastDay").toString(),Integer.parseInt(document.get("gauge").toString()));
                if(DateModule.compareDay(document.get("lastDay").toString(),DateModule.getToday())>1){ //하루이상 안썼을 때
                    Log.d("DataDB","Game Over"); //게임오버 추가
                }
                ds.setGauge(ds.getGauge()+1);
                ds.setLastDay(DateModule.getToday());

                db.collection("streak").add(ds).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                });
            }
        });
    }

/*    테스트용 코드
        DataDB db2 = new DataDB();
        ArrayList<String> test = new ArrayList<String>();
        test.add("asdsa");
        db2.setPostData(new PostData(user.getUid(),"제목","내용","x월x일xx시xx분",3.14,3.14,false,test, 3));*/

}
