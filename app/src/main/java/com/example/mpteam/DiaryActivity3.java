package com.example.mpteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryActivity3 extends AppCompatActivity {

    String day;
    TextView day_text;
    Button next_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary3);

        Intent intent = getIntent();
        day = intent.getStringExtra("day");

        day_text = findViewById(R.id.day_text);
        day_text.setText(day);

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryActivity3.this, DiaryActivity4.class);
                startActivity(intent);
                finish();
            }
        });
    }
}