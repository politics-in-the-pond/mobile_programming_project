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

    /*public void setPostData(PostData post){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("users").document(post.getUserId()).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult();
                                        if(document.exists()){
                                            UserData user = new UserData (post.getUserId(), document.getData().get("nickname").toString(), (ArrayList<String>) document.getData().get("userPosts"), (ArrayList<Integer>) document.getData().get("cardIndex"));
                                            user.getUserPosts().add()
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
*/
/*    public UserData getUserData(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            UserData user = new UserData (id, document.getData().get("nickname").toString(), (ArrayList<String>) document.getData().get("userPosts"), (ArrayList<Integer>) document.getData().get("cardIndex"));
                        }
                    }
                });
    }*/
}
