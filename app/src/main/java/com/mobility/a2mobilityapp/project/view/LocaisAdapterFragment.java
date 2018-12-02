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
    EditText editCasa;
    private Button btnSalvar;

    String casa;

    public LocaisAdapterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adapter_fragment_locais, container, false);

        return view;
    }
}
