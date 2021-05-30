package com.example.mpteam;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mpteam.CalendarClass.EmotionDecorator;
import com.example.mpteam.CalendarClass.OneDayDecorator;
import com.example.mpteam.CalendarClass.SaturdayDecorator;
import com.example.mpteam.CalendarClass.SundayDecorator;
import com.example.mpteam.data.Emotion;
import com.example.mpteam.data.PostData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EmotionStaticsActivity extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    int standardSize_X, standardSize_Y;
    float density;
    ArrayList<PostData> posts;
    ArrayList<CalendarDay> happy_day = new ArrayList<>();
    ArrayList<CalendarDay> smile_day = new ArrayList<>();
    ArrayList<CalendarDay> laughing_day = new ArrayList<>();
    ArrayList<CalendarDay> neutral_day = new ArrayList<>();
    ArrayList<CalendarDay> disapointment_day = new ArrayList<>();
    ArrayList<CalendarDay> sad_day = new ArrayList<>();
    ArrayList<CalendarDay> shocked_day = new ArrayList<>();
    ArrayList<CalendarDay> angry_day = new ArrayList<>();
    ArrayList<CalendarDay> crying_day = new ArrayList<>();

    public void GetMyPosts() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(user.getUid()).collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PostData new_post = new PostData();
                                new_post.setContent(document.get("content").toString());
                                new_post.setDateTime(document.get("dateTime").toString());
                                new_post.setEmotion(Integer.parseInt(document.get("emotion").toString()));
                                new_post.setContent(document.get("imageURL").toString());
                                new_post.setIspublic((boolean) document.get("ispublic"));
                                new_post.setLatitude((double) document.get("latitude"));
                                new_post.setLongitude((double) document.get("longitude"));
                                new_post.setTitle(document.get("title").toString());
                                new_post.setUserId(user.getUid());
                                posts.add(new_post);
                            }
                            parseDailyEmotion();
                        }
                    }
                });
    }

    public void parseDailyEmotion() {
        Log.v("tag", Integer.toString(posts.size()));
        for (int i = 0; i < posts.size(); i++) {
            int em = posts.get(i).getEmotion();
            String day = posts.get(i).getDateTime();
            Log.v("tag", day);
            String[] parsed = day.split("/");
            CalendarDay new_day = new CalendarDay(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]) - 1, Integer.parseInt(parsed[2]));
            Emotion new_emotion = Emotion.values()[em];
            if (new_emotion == Emotion.HAPPY) {
                happy_day.add(new_day);
            } else if (new_emotion == Emotion.SMILE) {
                smile_day.add(new_day);
            } else if (new_emotion == Emotion.LAUGING) {
                laughing_day.add(new_day);
            } else if (new_emotion == Emotion.NEUTRAL) {
                neutral_day.add(new_day);
            } else if (new_emotion == Emotion.DISSAPOINTMENT) {
                disapointment_day.add(new_day);
            } else if (new_emotion == Emotion.SAD) {
                sad_day.add(new_day);
            } else if (new_emotion == Emotion.SHOCKED) {
                shocked_day.add(new_day);
            } else if (new_emotion == Emotion.ANGRY) {
                angry_day.add(new_day);
            } else if (new_emotion == Emotion.CRYING) {
                crying_day.add(new_day);
            }

        }
        Fragment fragment = new GraphFragment(); // Fragment 생성
        Bundle bundle = new Bundle(9); // 파라미터는 전달할 데이터 개수
        bundle.putInt("happy_day", happy_day.size()); // key , value
        bundle.putInt("smile_day", smile_day.size());
        bundle.putInt("laughing_day", laughing_day.size());
        bundle.putInt("neutral_day", neutral_day.size());
        bundle.putInt("disapointment_day", disapointment_day.size());
        bundle.putInt("sad_day", sad_day.size());
        bundle.putInt("shocked_day", shocked_day.size());
        bundle.putInt("angry_day", angry_day.size());
        bundle.putInt("crying_day", crying_day.size());
        //화면에 보여지는 fragment를 추가하거나 바꿀 수 있는 객체를 만든다.
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.graph_viewer, fragment);
        transaction.commit();
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator(),
                new EmotionDecorator(happy_day, Emotion.HAPPY, this),
                new EmotionDecorator(smile_day, Emotion.SMILE, this),
                new EmotionDecorator(laughing_day, Emotion.LAUGING, this),
                new EmotionDecorator(neutral_day, Emotion.NEUTRAL, this),
                new EmotionDecorator(disapointment_day, Emotion.DISSAPOINTMENT, this),
                new EmotionDecorator(sad_day, Emotion.SAD, this),
                new EmotionDecorator(shocked_day, Emotion.SHOCKED, this),
                new EmotionDecorator(angry_day, Emotion.ANGRY, this),
                new EmotionDecorator(crying_day, Emotion.CRYING, this)
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_statics);
        happy_day = new ArrayList<>();
        smile_day = new ArrayList<>();
        laughing_day = new ArrayList<>();
        neutral_day = new ArrayList<>();
        disapointment_day = new ArrayList<>();
        sad_day = new ArrayList<>();
        shocked_day = new ArrayList<>();
        angry_day = new ArrayList<>();
        crying_day = new ArrayList<>();
        posts = new ArrayList<PostData>();
        GetMyPosts();
        materialCalendarView = findViewById(R.id.emotion_calendarview);
        //materialCalendarView.setTileHeightDp((int) (standardSize_Y / 10));
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth();
                int Day = date.getDay();
                String shot_Day = Year + "/" + Month + "/" + Day;
            }
        });
    }

}