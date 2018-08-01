package com.mobility.a2mobilityapp.project.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.mobility.a2mobilityapp.R;

public class CadastroPessoaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

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
