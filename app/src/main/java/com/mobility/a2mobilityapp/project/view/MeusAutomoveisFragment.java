package com.mobility.a2mobilityapp.project.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobility.a2mobilityapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeusAutomoveisFragment extends Fragment {

    Button btnConcluir;

    public MeusAutomoveisFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meus_automoveis, container, false);

        btnConcluir = (Button) view.findViewById(R.id.button);

        btnConcluir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    btnConcluir.setBackgroundColor(Color.rgb(0, 100, 0));
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    btnConcluir.setBackgroundColor(getResources().getColor(R.color.text_background_verde));
                }
                return false;
            }
        });

        return view;

    }

}
