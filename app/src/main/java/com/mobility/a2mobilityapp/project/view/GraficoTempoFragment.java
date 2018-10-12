package com.mobility.a2mobilityapp.project.view;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
            "Diário",
            "Semanal",
            "Mensal"
    };

    public GraficoTempoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grafico_tempo, container, false);

        pieChart = view.findViewById(R.id.piechart_1);
        comboBox = view.findViewById(R.id.spinnerTempo);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, dias);
        comboBox.setAdapter(arrayAdapter);

        //Método do Spinner para capturar o item selecionado
        comboBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                String nome = parent.getItemAtPosition(posicao).toString();
                if(nome.equals("Diário")){
                    int valorCalculo = 1;
                    int valorTransportePublico = 10;
                    int valorUber = 20;
                    int valorCarroProprio = 30;
                    //TransportePublico, Uber, CarroProprio
                    setPieChart((valorTransportePublico*valorCalculo),(valorUber*valorCalculo),(valorCarroProprio*valorCalculo));
                }
                if(nome.equals("Semanal")){
                    int valorCalculo = 7;
                    int valorTransportePublico = 5;
                    int valorUber = 15;
                    int valorCarroProprio = 25;
                    //TransportePublico, Uber, CarroProprio
                    setPieChart((valorTransportePublico*valorCalculo),(valorUber*valorCalculo),(valorCarroProprio*valorCalculo));
                }
                if(nome.equals("Mensal")){
                    int valorCalculo = 30;
                    int valorTransportePublico = 20;
                    int valorUber = 40;
                    int valorCarroProprio = 60;
                    //TransportePublico, Uber, CarroProprio
                    setPieChart((valorTransportePublico*valorCalculo),(valorUber*valorCalculo),(valorCarroProprio*valorCalculo));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    //Grafico Tempo
    public void setPieChart(int transportePublico, int uber, int carroProprio) {
        this.pieChart = pieChart;
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
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
