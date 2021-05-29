package com.example.mpteam.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostData implements Serializable {
    //String postId;
    String userId, title, content, dateTime;
    double latitude, longitude;
    boolean ispublic;
    ArrayList<String> imageURL;
    int emotion; //0기쁨 1보통 2우울 3미묘 이런 형태로


    public void addImageURL(String s) {
        this.imageURL.add(s);
    }

}
