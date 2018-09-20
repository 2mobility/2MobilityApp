package com.mobility.a2mobilityapp.project.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.utils.Mask;

public class CadastroPessoaActivity extends AppCompatActivity {

    private EditText campoCpf;
    private EditText campoCelular;
    private EditText campoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        campoCelular = (EditText) findViewById(R.id.editText2);
        campoCelular.addTextChangedListener(Mask.mask(campoCelular, Mask.FORMAT_FONE_CELULAR));

        campoData = (EditText) findViewById(R.id.editText3);
        campoData.addTextChangedListener(Mask.mask(campoData, Mask.FORMAT_DATE));

        campoCpf = (EditText) findViewById(R.id.editText4);
        campoCpf.addTextChangedListener(Mask.mask(campoCpf, Mask.FORMAT_CPF));

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Switch switchId = (Switch) findViewById(R.id.switch2); //busquei pelo id
                Boolean status = switchId.isChecked(); //checando os status (true / false)

                if (status == true) {
                    setContentView(R.layout.fragment_cadastro_automovel);
                }
            }

        });
    }
}
