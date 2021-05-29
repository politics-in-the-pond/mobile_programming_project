package com.example.mpteam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DiaryFragment extends Fragment {

    ViewGroup viewGroup;
    LinearLayout btn1, btn2, btn3, btn4;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_diary, container, false);


        btn1 = viewGroup.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "달";
                String period = "일주일";
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("period", period);
                startActivity(intent);
            }
        });
        btn2 = viewGroup.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "화성";
                String period = "2주";
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("period", period);
                startActivity(intent);
            }
        });
        btn3 = viewGroup.findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "토성";
                String period = "3주";
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("period", period);
                startActivity(intent);
            }
        });
        btn4 = viewGroup.findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "천왕성";
                String period = "한달";
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("period", period);
                startActivity(intent);
            }
        });

        return viewGroup;
    }

}

