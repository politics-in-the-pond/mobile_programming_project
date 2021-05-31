package com.example.mpteam;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mpteam.data.CardData;
import com.example.mpteam.modules.CardAdapter;
import com.example.mpteam.modules.MyGridViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BoxFragment extends Fragment {
    ViewGroup viewGroup;
    ArrayList<CardData> items;
    GridView grid;
    CardAdapter adapter;

    @Override
    public void onResume(){
        super.onResume();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                ArrayList<Number> cards = (ArrayList<Number>) document.get("cardIndex");
                for(int i = 0; i < cards.size(); i++){
                    adapter.toggle(cards.get(i).intValue());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_box, container, false);
        grid = viewGroup.findViewById(R.id.frag_excard_box);
        items = new ArrayList<>();
        items.add(new CardData(0, R.drawable.first));
        items.add(new CardData(1, R.drawable.excard11_1wk));
        items.add(new CardData(2, R.drawable.excard12_1wk));
        items.add(new CardData(3, R.drawable.excard13_1wk));
        items.add(new CardData(4, R.drawable.excard8_2wk));
        items.add(new CardData(5, R.drawable.excard9_2wk));
        items.add(new CardData(6, R.drawable.excard5_3wk));
        items.add(new CardData(7, R.drawable.excard6_3wk));
        items.add(new CardData(8, R.drawable.excard1_4wk));
        items.add(new CardData(9, R.drawable.excard2_4wk));
        items.add(new CardData(10, R.drawable.excard4_4wk));
        items.add(new CardData(11, R.drawable.excard7_4wk));
        items.add(new CardData(12, R.drawable.excard10_4wk));
        adapter = new CardAdapter(items, getContext());
        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return viewGroup;
    }
}

