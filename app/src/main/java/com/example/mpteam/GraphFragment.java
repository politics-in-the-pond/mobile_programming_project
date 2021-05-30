package com.example.mpteam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mpteam.GraphClass.CustomRenderer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class GraphFragment extends Fragment {

    ViewGroup viewGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_graph, container, false);
        String title = "감정 통계";
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, getArguments().getInt("happy_day")));
        entries.add(new BarEntry(1f, getArguments().getInt("smile_day")));
        entries.add(new BarEntry(2f, getArguments().getInt("laughing_day")));
        entries.add(new BarEntry(3f, getArguments().getInt("neutral_day")));
        entries.add(new BarEntry(4f, getArguments().getInt("disapointment_day")));
        entries.add(new BarEntry(5f, getArguments().getInt("sad_day")));
        entries.add(new BarEntry(6f, getArguments().getInt("shocked_day")));
        entries.add(new BarEntry(7f, getArguments().getInt("angry_day")));
        entries.add(new BarEntry(8f, getArguments().getInt("crying_day")));
        BarChart barChart = (BarChart) viewGroup.findViewById(R.id.chart);

        BarDataSet depenses = new BarDataSet(entries, title);
        depenses.setValueFormatter(new IntegerFormatter());
        depenses.setValueTextSize(20);
        //depenses.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<IBarDataSet> datasetsarr = new ArrayList<>();
        datasetsarr.add(depenses);
        BarData data = new BarData(datasetsarr); // 라이브러리 v3.x 사용하면 에러 발생함
        depenses.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.animateXY(1000, 1000);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.getAxisLeft().setSpaceBottom(50f);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setEnabled(false);
        barChart.setRenderer(new CustomRenderer(barChart, barChart.getAnimator(), barChart.getViewPortHandler(), getContext()));
        barChart.setDrawMarkers(true);
        // XAxis
        barChart.setDrawValueAboveBar(false);
        barChart.getLegend().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.setData(data);
        barChart.invalidate();
        return viewGroup;
    }


    public class IntegerFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public IntegerFormatter() {
            mFormat = new DecimalFormat("###,###,###"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value); // e.g. append a dollar-sign
        }
    }

}