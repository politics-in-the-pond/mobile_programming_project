package com.example.mpteam;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyPageFragment extends Fragment {


    ViewGroup viewGroup;
    Button emotion_btn;
    LinearLayout emotion;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage,container,false);

        emotion = viewGroup.findViewById(R.id.emotion);
        emotion_btn = viewGroup.findViewById(R.id.emotion_btn);
        emotion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotion.setVisibility(View.VISIBLE);
            }
        });

        return viewGroup;
    }
}

