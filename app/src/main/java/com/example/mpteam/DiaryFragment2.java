package com.example.mpteam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mpteam.modules.DateModule;
import com.example.mpteam.modules.MyGridViewAdapter;

import java.util.ArrayList;

public class DiaryFragment2 extends Fragment {

    String day;
    TextView day_text;
    Button next_btn;
    ViewGroup viewGroup;
    LinearLayout btn1, btn2, btn3, btn4;
    ArrayList<Bitmap> items;
    GridView gridView;
    View select = null;
    int select_emotion=0;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_diary2, container, false);


        gridView = viewGroup.findViewById(R.id.emotion_gridview);

        items = new ArrayList<>();
        Bitmap happy = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.happy);
        Bitmap smile = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.smile);
        Bitmap laughing = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.laughing);
        Bitmap neutral = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.neutral);
        Bitmap disappointment = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.disappointment);
        Bitmap sad = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sad);
        Bitmap shocked = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.shocked);
        Bitmap angry = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.angry);
        Bitmap crying = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.crying);



        items.add(happy);
        items.add(laughing);
        items.add(smile);
        items.add(sad);
        items.add(angry);
        items.add(disappointment);
        items.add(crying);
        items.add(shocked);
        items.add(neutral);
        MyGridViewAdapter adapter = new MyGridViewAdapter(items, getContext());

        gridView.setAdapter(adapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(select != null)
                    select.setBackgroundColor(0x00000000);
                select=view;
                select.setBackgroundColor(Color.RED);
                select_emotion=position;
                return false;
            }
        });


        next_btn = viewGroup.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(select != null) {
                    Intent intent = new Intent(getActivity(), com.example.mpteam.DiaryActivity3.class);
                    intent.putExtra("day", DateModule.getToday());
                    intent.putExtra("select", select_emotion);
                    getActivity().startActivityForResult(intent,1);
                }
            }
        });
        return viewGroup;
    }
}