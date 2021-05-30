package com.example.mpteam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DiaryFragment extends Fragment {

    ViewGroup viewGroup;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    LinearLayout btn1, btn2, btn3, btn4;
    Button btn_useCoin;
    ImageView coinImage;
    TextView coin;
    ProgressBar progressBar;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_diary, container, false);
        refresh();

        coinImage = viewGroup.findViewById(R.id.coinImage);
        coin = viewGroup.findViewById(R.id.coinText);
        progressBar = viewGroup.findViewById(R.id.progress);

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
        btn_useCoin = (Button) viewGroup.findViewById(R.id.useCoin);
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

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    public void refresh(){
        db.collection("streak").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                DiaryStreak ds = new DiaryStreak(user.getUid(),document.get("startDay").toString(), document.get("lastDay").toString(), Integer.parseInt(document.get("gauge").toString()));
                if(DateModule.compareDay(ds.getLastDay(),DateModule.getToday())>1){
                    Toast.makeText(getContext(),"임무 실패!",Toast.LENGTH_SHORT).show();
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

