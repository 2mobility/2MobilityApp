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
import android.widget.Toast;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.utils.Mask;
import com.mobility.a2mobilityapp.project.utils.ValidadorCPF;
import com.mobility.a2mobilityapp.project.utils.ValidadorEmail;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    Button btnConcluir;
    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoCpf;
    private EditText campoCelular;
    private EditText campoData;
    private ValidadorCPF validadorCpf = new ValidadorCPF();
    private ValidadorEmail validadorEmail = new ValidadorEmail();

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        btnConcluir = (Button) view.findViewById(R.id.button);

        campoNome = (EditText) view.findViewById(R.id.editText);
        campoEmail = (EditText) view.findViewById(R.id.editText10);
        campoCpf = (EditText) view.findViewById(R.id.editText7);
        campoCpf.addTextChangedListener(Mask.mask(campoCpf, Mask.FORMAT_CPF));
        campoCelular = (EditText) view.findViewById(R.id.editText9);
        campoCelular.addTextChangedListener(Mask.mask(campoCelular, Mask.FORMAT_FONE_CELULAR));
        campoData = (EditText) view.findViewById(R.id.editText11);
        campoData.addTextChangedListener(Mask.mask(campoData, Mask.FORMAT_DATE));

        btnConcluir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // trata os campos vazios
                if(!campoNome.getText().toString().equals("") && !campoCelular.getText().toString().equals("") &&
                        !campoData.getText().toString().equals("") && !campoCpf.getText().toString().equals("") &&
                        !campoEmail.getText().toString().equals("")){
                    //verificar email
                    if(validadorEmail.isEmail(campoEmail.getText().toString())){
                        //trata CPF
                        String cpf = campoCpf.getText().toString().replace(".","").replace("-","");
                        if(validadorCpf.isCPF(cpf)){
                            /*
                             Mandar para api
                             */
                        }else{
                            Toast.makeText(getContext(),"CPF inválido.",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(),"E-mail inválido. Forneça um e-mail existente.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(),"Atenção! Existem campos a serem preenchidos.",Toast.LENGTH_SHORT).show();
                }

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
