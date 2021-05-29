package com.example.mpteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    String name, period;
    TextView name_text, period_text;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        period = intent.getStringExtra("period");

        name_text = findViewById(R.id.name_text);
        name_text.setText("[" + name + "]");
        period_text = findViewById(R.id.period_text);
        period_text.setText("[" + period + "]");

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
    }
}