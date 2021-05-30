package com.example.mpteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mpteam.data.PostData;
import com.example.mpteam.databinding.ActivityDiary3Binding;
import com.example.mpteam.modules.DataDB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import lombok.NonNull;

public class DiaryActivity3 extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;
    String day;
    Button next_btn;
    ImageView gallery;
    ActivityDiary3Binding binding;
    SharedPreferences pref;
    Uri file = null;
    PostData post;
    FirebaseUser user;
    DataDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 기존의 코드
//        setContentView(R.layout.activity_diary3);
        // 데이터 바인딩 시험
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary3);
        pref = getSharedPreferences("pref", DiaryActivity3.MODE_PRIVATE);

        Intent intent = getIntent();
        day = intent.getStringExtra("day");

        binding.dayText.setText(day);
//        이전의 코드
//        day_text = findViewById(R.id.day_text);
//        day_text.setText(day);

        db = new DataDB();
        user = FirebaseAuth.getInstance().getCurrentUser();

        gallery = findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentimg = new Intent(Intent.ACTION_PICK);
                intentimg.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentimg, GET_GALLERY_IMAGE);
            }
        });


        // 기록버튼
        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                UploadTask uploadTask;

                if (file != null) {
                    StorageReference imageRef = storageRef.child("images/" + file.getLastPathSegment());
                    uploadTask = (UploadTask) imageRef.putFile(file).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri imageUrl = uri;

                                    Log.d("TAG", imageUrl.toString());

                                    post = new PostData();
                                    post.setUserId(user.getUid());
                                    post.setTitle("제목");
                                    post.setContent(binding.writing.getText().toString());
                                    post.setDateTime(day);
                                    post.setIspublic(false);
                                    post.setLatitude(3.14);
                                    post.setLongitude(3.14);
                                    post.setEmotion(1);
                                    post.setImageURL(new ArrayList<String>());
                                    post.addImageURL(imageUrl.toString());

                                    db.setPostData(post);

                                    Intent intent = new Intent(DiaryActivity3.this, DiaryActivity4.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    });
                } else {
                    post = new PostData();
                    post.setUserId(user.getUid());
                    post.setTitle("제목");
                    post.setContent(binding.writing.getText().toString());
                    post.setDateTime(day);
                    post.setIspublic(false);
                    post.setLatitude(3.14);
                    post.setLongitude(3.14);
                    post.setEmotion(1);
                    post.setImageURL(new ArrayList<String>());
                    post.addImageURL("");

                    db.setPostData(post);

                    Intent intent = new Intent(DiaryActivity3.this, DiaryActivity4.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        binding.map.setOnClickListener(new Map());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            file = selectedImageUri;
        }
    }


    private class Map implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent mapIntent = new Intent(DiaryActivity3.this, MapActivity.class);
            startActivityForResult(mapIntent, 0);
        }
    }
}