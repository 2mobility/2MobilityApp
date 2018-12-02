package com.mobility.a2mobilityapp.project.utils;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.bean.MeioTransporte;
import com.mobility.a2mobilityapp.project.bean.Particular;
import com.mobility.a2mobilityapp.project.bean.TransportePublico;
import com.mobility.a2mobilityapp.project.bean.Uber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MeusLocaisFragment extends Fragment {

    public MeusLocaisFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meus_locais, container, false);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> hm = new HashMap<String, String>();

        hm.put("listview_imagem_casa", Integer.toString((R.drawable.ic_casa)));
        hm.put("listview_titulo_casa", "Casa");

        hm.put("listview_imagem_trabalho", Integer.toString((R.drawable.ic_trabalho)));
        hm.put("listview_titulo_trabalho", "Trabalho");

        aList.add(hm);


        String[] from = {"listview_imagem_casa", "listview_titulo_casa", "listview_imagem_trabalho", "listview_titulo_trabalho"};
        int[] to = {R.id.listview_imagem_casa, R.id.listview_titulo_casa, R.id.listview_imagem_trabalho, R.id.listview_titulo_trabalho};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.adapter_fragment_locais, from, to);
        ListView androidListView = (ListView) view.findViewById(R.id.list_view_locais);
        androidListView.setAdapter(simpleAdapter);

        return view;
    }
}
