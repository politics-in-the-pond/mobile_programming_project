package com.example.mpteam;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyPageFragment extends Fragment {

    private FirebaseAuth mAuth;
    ViewGroup viewGroup;
    FirebaseFirestore firebaseFirestore;
    DocumentReference docRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView nickname;
    TextView email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage,container,false);
        mAuth = FirebaseAuth.getInstance();
        nickname =viewGroup.findViewById(R.id.Mypage_nickname);
        email = viewGroup.findViewById(R.id.Mypage_email);

        if (user != null) {
            // Name, email address, and profile photo Url
            setNickName();
            email.setText(user.getEmail());
        }
        return viewGroup;
    }

    private void setNickName(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        docRef = firebaseFirestore.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nickname.setText(document.getData().get("nickname").toString());
                    }
                }
            }
    });
    }
}

