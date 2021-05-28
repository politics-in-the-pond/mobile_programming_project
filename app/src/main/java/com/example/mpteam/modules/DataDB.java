package com.example.mpteam.modules;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mpteam.data.PostData;
import com.example.mpteam.data.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DataDB {
    public void setUserData(UserData user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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

    public void setPostData(PostData post){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(post.getUserId()).collection("posts").add(post)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d("TAG", "업로드 완료");
                    }
                });
    }

/*    테스트용 코드
        DataDB db2 = new DataDB();
        ArrayList<String> test = new ArrayList<String>();
        test.add("asdsa");
        db2.setPostData(new PostData(user.getUid(),"제목","내용","x월x일xx시xx분",3.14,3.14,false,test, 3));*/

}
