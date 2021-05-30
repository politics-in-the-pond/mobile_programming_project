package com.example.mpteam.GraphClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.mpteam.R;
import com.example.mpteam.data.Emotion;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class CustomRenderer extends BarChartRenderer {

    private Paint myPaint;
    private Context context;
    public CustomRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler,Context context) {
        super(chart, animator, viewPortHandler);
        this.myPaint = new Paint();
        this.context = context;
    }
    @Override
    public void drawValues(Canvas c) {
        super.drawValues(c);
        // you can modify the original method
        // so that everything is drawn on the canvas inside a single loop
        // also you can add logic here to meet your requirements
        int colorIndex = 0;
        for (int i = 0; i < mChart.getBarData().getDataSetCount(); i++) {
            BarBuffer buffer = mBarBuffers[i];
        }
    }
    @Override
    public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
        String text = formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler);
        Paint paintStyleOne = new Paint(mValuePaint);
        c.drawText(text, x, y+50f,paintStyleOne);
        Emotion emotion = Emotion.values()[(int)entry.getX()];
        Bitmap image = BitmapFactory.decodeResource(context.getResources(),emotion_to_image(emotion));
        image= image.createScaledBitmap(image,50,50,true);
        c.drawBitmap(image,x-25,500,paintStyleOne);
        //else{
//            super.drawValue(c, formatter, value, entry, dataSetIndex, x, y, color);
        //}
    }
    public int emotion_to_image(Emotion emotion) {
        if(emotion==Emotion.HAPPY)
        {
            return R.drawable.happy;
        }
        if(emotion==Emotion.SMILE)
        {
            return R.drawable.smile;
        }
        if(emotion==Emotion.LAUGING)
        {
            return R.drawable.laughing;
        }
        if(emotion==Emotion.NEUTRAL)
        {
            return R.drawable.neutral;
        }
        if(emotion==Emotion.DISSAPOINTMENT)
        {
            return R.drawable.disappointment;
        }
        if(emotion==Emotion.SAD)
        {
            return R.drawable.sad;
        }
        if(emotion==Emotion.SHOCKED)
        {
            return R.drawable.shocked;
        }
        if(emotion==Emotion.ANGRY)
        {
            return R.drawable.angry;
        }
        if(emotion==Emotion.CRYING)
        {
            return R.drawable.crying;
        }
        return 0;
    }
}