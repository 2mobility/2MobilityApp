package com.mobility.a2mobilityapp.project.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobility.a2mobilityapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroAutomovelFragment extends Fragment {


    public CadastroAutomovelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro_automovel, container, false);
    }

}
