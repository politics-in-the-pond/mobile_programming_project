package com.example.mpteam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpteam.data.Planet;
import com.example.mpteam.modules.DateModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.type.DateTime;

public class StartActivity extends AppCompatActivity {

    String name, period;
    int image;
    TextView name_text, period_text;
    Button next_btn;
    ImageView planet;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        period = intent.getStringExtra("period");
        image = intent.getIntExtra("image",0);
        Bitmap bt = BitmapFactory.decodeResource(getResources(),image);
        planet=findViewById(R.id.planet);
        name_text = findViewById(R.id.name_text);
        name_text.setText("[" + name + "]");
        period_text = findViewById(R.id.period_text);
        period_text.setText("[" + period + "일]");
        planet.setImageBitmap(bt);

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData("streak","startDay", DateModule.getToday());
                UpdateDataInteger("streak","period", Integer.parseInt(period));
            }
        });
    }
    private void UpdateDataInteger(String collection, String field, int content) {
        db = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        final DocumentReference sfDocRef = db.collection(collection).document(uid);

        db.runTransaction(new Transaction.Function<Void>() {
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
                setResult(RESULT_OK);
                finish();
                Log.d("scmsg", "Transaction success!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        setResult(RESULT_CANCELED);
                        finish();
                        Log.w("fmsg", "Transaction failure.", e);
                    }

                });
    }

    private void UpdateData(String collection, String field, String content) {
        db = FirebaseFirestore.getInstance();
        String uid = user != null ? user.getUid() : null;
        final DocumentReference sfDocRef = db.collection(collection).document(uid);

        db.runTransaction(new Transaction.Function<Void>() {
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
                setResult(RESULT_OK);
                finish();
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