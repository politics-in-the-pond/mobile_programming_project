package com.example.mpteam.CalendarClass;

import android.content.Context;

import com.example.mpteam.R;
import com.example.mpteam.data.Emotion;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;

public class EmotionDecorator implements DayViewDecorator {

    private ArrayList<CalendarDay> dates;
    private Emotion emotion;
    private Context context;

    public EmotionDecorator(ArrayList<CalendarDay> dates, Emotion emotion, Context context) {
        this.dates = dates;
        this.emotion = emotion;
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(context.getResources().getDrawable(emotion_to_image()));
    }

    public int emotion_to_image() {
        if (emotion == Emotion.HAPPY) {
            return R.drawable.happy;
        }
        if (emotion == Emotion.SMILE) {
            return R.drawable.smile;
        }
        if (emotion == Emotion.LAUGING) {
            return R.drawable.laughing;
        }
        if (emotion == Emotion.NEUTRAL) {
            return R.drawable.neutral;
        }
        if (emotion == Emotion.DISSAPOINTMENT) {
            return R.drawable.disappointment;
        }
        if (emotion == Emotion.SAD) {
            return R.drawable.sad;
        }
        if (emotion == Emotion.SHOCKED) {
            return R.drawable.shocked;
        }
        if (emotion == Emotion.ANGRY) {
            return R.drawable.angry;
        }
        if (emotion == Emotion.CRYING) {
            return R.drawable.crying;
        }
        return 0;
    }
}

