package com.mobility.a2mobilityapp.project.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.utils.Mask;

public class SenhaActivity extends AppCompatActivity {

    private EditText campoCpf;
    private EditText campoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        campoCpf = (EditText) findViewById(R.id.editText14);
        campoCpf.addTextChangedListener(Mask.mask(campoCpf, Mask.FORMAT_CPF));

        campoData = (EditText) findViewById(R.id.editText);
        campoData.addTextChangedListener(Mask.mask(campoData, Mask.FORMAT_DATE));

    }
}
