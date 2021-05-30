package com.example.mpteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mpteam.data.DiaryStreak;
import com.example.mpteam.modules.DataDB;
import com.example.mpteam.modules.DateModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class MyPageFragment extends Fragment {

    ViewGroup viewGroup;
    FirebaseFirestore firebaseFirestore;
    DocumentReference docRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView profile;
    ImageView imageView;
    TextView nickname;
    TextView email;
    Button btn_change_nickname;
    Button btn_change_image;
    Button btn_change_email;
    Button btn_change_pw;
    Button btn_logout;
    Button btn_emotion_statics;
    AlertDialog nickname_change_dialog;
    AlertDialog email_change_dialog;
    AlertDialog image_change_dialog;
    AlertDialog pw_check_dialog;
    AlertDialog pw_change_dialog;
    AlertDialog logout_dialog;
    Uri file;
    Bitmap image;
    private FirebaseAuth mAuth;
    Button btn_useCoin;
    ImageView coinImage;
    TextView coin;
    ProgressBar progressBar;

    public void create_logout_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("로그아웃 하시겠습니까?")
                .setTitle("로그아웃");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        logout_dialog = builder.create();
    }

    public void create_nickname_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        builder.setTitle("닉네임 변경");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (TextUtils.isEmpty(input.getText().toString())) {
                            Toast.makeText(getActivity(), "Please input nickname.", Toast.LENGTH_LONG).show();
                        } else {
                            UpdateUserData("nickname", input.getText().toString());
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    DisplayNickName();
                                }
                            }, 500);

                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });


        nickname_change_dialog = builder.create();
    }

    public void create_image_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        DataDB datadb = new DataDB();

        Map<String, Object> map = new HashMap<String, Object>();

        Button button = new Button(getContext());
        button.setText("이미지 선택");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentimg = new Intent(Intent.ACTION_PICK);
                intentimg.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentimg, 200);
            }
        });

        /*imageView = new ImageView(getContext());
        imageView.setMaxHeight(500);
        imageView.setMaxWidth(500);
        imageView.setImageResource(R.drawable.ic_outline_account_circle_24);
        if(image!=null){
            imageView.setImageBitmap(image);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentimg = new Intent(Intent.ACTION_PICK);
                intentimg.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentimg, 200);
            }
        });*/
        builder.setTitle("프로필사진 변경");
        builder.setView(button);
        /*builder.setView(imageView);*/
        builder.setPositiveButton("적용",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (file == null) return;
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        UploadTask uploadTask;
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
                                        map.put("image", imageUrl.toString());
                                        db.collection("users").document(user.getUid()).update(map);
                                        new DownloadFilesTask().execute(imageUrl.toString());
                                    }
                                });
                            }
                        });
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        image_change_dialog = builder.create();
    }

    public void create_pw_change_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setTitle("새 비밀번호");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (TextUtils.isEmpty(input.getText().toString())) {
                            Toast.makeText(getContext(), "Please input password.", Toast.LENGTH_SHORT).show();
                        } else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", input.getText().toString())) {
                            Toast.makeText(getContext(), "Please keep the password format.", Toast.LENGTH_SHORT).show();
                        } else {
                            user.updatePassword(input.getText().toString());
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });


        pw_change_dialog = builder.create();
    }

    public void create_pw_check_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setTitle("비밀번호 입력");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mAuth.signInWithEmailAndPassword(user.getEmail(), input.getText().toString())
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            pw_change_dialog.show();
                                        } else {
                                            Toast.makeText(getContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                                        }
                                    }
                                });

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });


        pw_check_dialog = builder.create();
    }

    public void create_email_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        builder.setTitle("이메일 변경");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (TextUtils.isEmpty(input.getText().toString())) {
                            Toast.makeText(getContext(), "Please input E-mail.", Toast.LENGTH_SHORT).show();
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input.getText().toString()).matches()) {
                            Toast.makeText(getContext(), "Not in email format\n", Toast.LENGTH_SHORT).show();

                        } else {
                            user.updateEmail(input.getText().toString());
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    email.setText(user.getEmail());
                                }
                            }, 500);

                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });


        email_change_dialog = builder.create();
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        mAuth = FirebaseAuth.getInstance();
        profile = viewGroup.findViewById(R.id.imageview);
        nickname = viewGroup.findViewById(R.id.Mypage_nickname);
        email = viewGroup.findViewById(R.id.Mypage_email);
        coinImage = viewGroup.findViewById(R.id.coinImage);
        btn_change_nickname = viewGroup.findViewById(R.id.change_nickname);
        btn_change_image = viewGroup.findViewById(R.id.change_image);
        btn_change_email = viewGroup.findViewById(R.id.change_email);
        btn_change_pw = viewGroup.findViewById(R.id.change_pw);
        btn_logout = viewGroup.findViewById(R.id.logout_btn);
        btn_emotion_statics = viewGroup.findViewById(R.id.btn_emotion_statics);
        btn_useCoin = viewGroup.findViewById(R.id.useCoin);
        coin = viewGroup.findViewById(R.id.coinText);
        progressBar = viewGroup.findViewById(R.id.progress);
        create_nickname_dialog();
        create_email_dialog();
        create_image_dialog();
        create_pw_check_dialog();
        create_pw_change_dialog();
        create_logout_dialog();

        refresh();
        if (user != null) {
            // Name, email address, and profile photo Url
            DisplayNickName();
            email.setText(user.getEmail());
        }

        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String imageUrl = document.get("image").toString();
                                Log.d("TAG", imageUrl);
                                new DownloadFilesTask().execute(imageUrl);
                            } else {
                            }
                        } else {
                        }
                    }
                });

        btn_change_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname_change_dialog.show();
            }
        });
        btn_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_change_dialog.show();
            }
        });
        btn_change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_change_dialog.show();
            }
        });
        btn_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_check_dialog.show();
            }
        });
        btn_emotion_statics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EmotionStaticsActivity.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_dialog.show();
            }
        });
        btn_useCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataDB data = new DataDB();
                data.useCoin(getContext());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refresh();
            }
        });
        return viewGroup;

    }

    private void DisplayNickName() {
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

    private void UpdateUserData(String field, String content) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        final DocumentReference sfDocRef = firebaseFirestore.collection("users").document(uid);

        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sfDocRef);
                transaction.update(sfDocRef, field, content);
                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("scmsg", "Transaction success!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fmsg", "Transaction failure.", e);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            file = selectedImageUri;
            //imageView.setImageURI(file);
        }
    }

    private class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmp = null;
            if (strings[0] == null || strings[0].equals("")) {
                return bmp;
            }
            try {
                String img_url = strings[0]; //url of the image
                URL url = new URL(img_url);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                image = result;
                return;
            }
            image = result;
            profile.setImageBitmap(image);
        }
    }

    public void refresh() {
        db.collection("streak").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                DiaryStreak ds = new DiaryStreak(user.getUid(), document.get("startDay").toString(), document.get("lastDay").toString(), Integer.parseInt(document.get("gauge").toString()));
                if (DateModule.compareDay(ds.getLastDay(), DateModule.getToday()) > 1) {
                    Toast.makeText(getContext(), "임무 실패!", Toast.LENGTH_SHORT).show();
                }
                if(ds.getGauge()/7>2){
                    coinImage.setImageResource(R.drawable.fuel_coin_yellow);
                    coin.setText("  x" + Integer.toString(3));
                    progressBar.setProgress(7);
                } else if(ds.getGauge()/7>0) {
                    coinImage.setImageResource(R.drawable.fuel_coin_green);
                    coin.setText("  x" + Integer.toString(ds.getGauge() / 7));
                    progressBar.setProgress(ds.getGauge() % 7);
                } else {
                    coinImage.setImageResource(R.drawable.gauge);
                    coin.setText("  x" + Integer.toString(ds.getGauge() / 7));
                    progressBar.setProgress(ds.getGauge() % 7);
                }
            }
        });
    }
}
