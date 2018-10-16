package com.mobility.a2mobilityapp.project.utils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobility.a2mobilityapp.MenuActivity;
import com.mobility.a2mobilityapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocaisAdapterFragment extends Fragment {

    private ImageView clear_casa;
    private EditText edit_casa;

    public LocaisAdapterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.adapter_fragment_locais, container, false);

        clear_casa = (ImageView) view.findViewById(R.id.clear_casa);
        edit_casa = (EditText) view.findViewById(R.id.edit_casa);

        clear_casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MenuActivity.this,"Atenção! Preencha o endereço de origem e destino.",Toast.LENGTH_SHORT).show();
                edit_casa.setText("oi");
            }
        });



        return view;
    }

}
