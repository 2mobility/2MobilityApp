package com.mobility.a2mobilityapp.project.view;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.mobility.a2mobilityapp.R;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mobility.a2mobilityapp.R;

import java.util.ArrayList;

public class GraficoTempoFragment extends Fragment {

    PieChart pieChart;

    Spinner comboBox;

    private String[] dias = new String[]{
            "Semanal",
            "Quinzenal",
            "Mensal"
    };

    public GraficoTempoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grafico_tempo, container, false);

        pieChart = view.findViewById(R.id.piechart_1);
        comboBox = view.findViewById(R.id.spinnerTempo);

        setPieChart(30,30,30);
        return view;
    }

    //Grafico Tempo
    public void setPieChart(int transportePublico, int uber, int carroProprio) {
        this.pieChart = pieChart;
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(transportePublico,"Transporte Público"));
        yValues.add(new PieEntry(uber,"Uber"));
        yValues.add(new PieEntry(carroProprio,"Carro Próprio"));

        PieDataSet dataSet = new PieDataSet(yValues, "Tempo");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setData(pieData);
    }
}
