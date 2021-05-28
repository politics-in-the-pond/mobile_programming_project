package com.example.mpteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import com.example.mpteam.data.PostData;
import com.example.mpteam.databinding.ActivityDiary3Binding;
import com.google.android.gms.common.util.DataUtils;
import com.google.firebase.firestore.FirebaseFirestore;

public class DiaryActivity3 extends AppCompatActivity {

    String day;
    Button next_btn;
    ActivityDiary3Binding binding;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 기존의 코드
//        setContentView(R.layout.activity_diary3);
        // 데이터 바인딩 시험
        binding = DataBindingUtil.setContentView(this,R.layout.activity_diary3);
        pref = getSharedPreferences("pref", DiaryActivity3.MODE_PRIVATE);

        Intent intent = getIntent();
        day = intent.getStringExtra("day");

        binding.dayText.setText(day);
//        이전의 코드
//        day_text = findViewById(R.id.day_text);
//        day_text.setText(day);


        // 기록버튼
        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWriting();
                Intent intent = new Intent(DiaryActivity3.this, DiaryActivity4.class);
                startActivity(intent);
                finish();
            }
        });
    }


    // 일기 업로드
    public void updateWriting() {
        FirebaseFirestore database;

        PostData postData = new PostData();
        postData.setContent(binding.writing.getText().toString());
        postData.setUserId(pref.getString("userToken", null));

    }




}