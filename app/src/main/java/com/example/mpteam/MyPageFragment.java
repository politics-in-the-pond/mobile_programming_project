package com.example.mpteam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.regex.Pattern;

public class MyPageFragment extends Fragment {

    private FirebaseAuth mAuth;
    ViewGroup viewGroup;
    FirebaseFirestore firebaseFirestore;
    DocumentReference docRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView nickname;
    TextView email;
    Button btn_change_nickname;
    Button btn_change_email;
    Button btn_change_pw;
    Button btn_logout;
    AlertDialog nickname_change_dialog;
    AlertDialog email_change_dialog;
    AlertDialog pw_check_dialog;
    AlertDialog pw_change_dialog;
    AlertDialog logout_dialog;
    public void create_logout_dialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage("로그아웃 하시겠습니까?")
                .setTitle("로그아웃");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getContext(),LoginActivity.class);
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
    public void create_nickname_dialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        builder.setTitle("닉네임 변경");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (TextUtils.isEmpty(input.getText().toString())) {
                            Toast.makeText(getActivity(), "Please input nickname.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            UpdateUserData("nickname",input.getText().toString());
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
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
    public void create_pw_change_dialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setTitle("새 비밀번호");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (TextUtils.isEmpty(input.getText().toString())) {
                            Toast.makeText(getContext(), "Please input password.", Toast.LENGTH_SHORT).show();
                        }
                        else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", input.getText().toString())) {
                            Toast.makeText(getContext(), "Please keep the password format.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
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
    public void create_pw_check_dialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
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
                                        if(task.isSuccessful())
                                        {
                                            pw_change_dialog.show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getContext(),"비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT);
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
    public void create_email_dialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        builder.setTitle("이메일 변경");
        builder.setView(input);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (TextUtils.isEmpty(input.getText().toString())) {
                            Toast.makeText(getContext(), "Please input E-mail.", Toast.LENGTH_SHORT).show();
                        }
                        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input.getText().toString()).matches()) {
                            Toast.makeText(getContext(), "Not in email format\n", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            user.updateEmail(input.getText().toString());
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage,container,false);
        mAuth = FirebaseAuth.getInstance();
        nickname =viewGroup.findViewById(R.id.Mypage_nickname);
        email = viewGroup.findViewById(R.id.Mypage_email);
        btn_change_nickname = viewGroup.findViewById(R.id.change_nickname);
        btn_change_email= viewGroup.findViewById(R.id.change_email);
        btn_change_pw =viewGroup.findViewById(R.id.change_pw);
        btn_logout=viewGroup.findViewById(R.id.logout_btn);
        create_nickname_dialog();
        create_email_dialog();
        create_pw_check_dialog();
        create_pw_change_dialog();
        create_logout_dialog();
        if (user != null) {
            // Name, email address, and profile photo Url
            DisplayNickName();
            email.setText(user.getEmail());
        }
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
        btn_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_check_dialog.show();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_dialog.show();
            }
        });
        return viewGroup;

    }

    private void DisplayNickName(){
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
    private void UpdateUserData(String field,String content){
        firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        final DocumentReference sfDocRef = firebaseFirestore.collection("users").document(uid);

        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sfDocRef);
                transaction.update(sfDocRef, field,content );
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
}

