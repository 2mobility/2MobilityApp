package com.mobility.a2mobilityapp.project.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobility.a2mobilityapp.LoginActivity;
import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.model.Automovel;
import com.mobility.a2mobilityapp.project.services.CrudOperation;
import com.mobility.a2mobilityapp.project.utils.VariablesGlobal;

public class CadastroAutomovelActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private EditText modelo;
    private EditText litro;
    private EditText preco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_automovel);
        //Workaround
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btnCadastrar = (Button) findViewById(R.id.button);
        modelo = (EditText)  findViewById(R.id.editText);
        litro = (EditText) findViewById(R.id.editText13);
        preco = (EditText) findViewById(R.id.editText18);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!modelo.getText().toString().equals("") && !litro.getText().equals("") && !preco.getText().toString().equals("")) {
                    Automovel automovel = new Automovel();
                    automovel.setApelido(modelo.getText().toString());
                    automovel.setMediaCombustivel(Double.parseDouble(preco.getText().toString().replace(",", ".")));
                    automovel.setKmLitro(Double.parseDouble(litro.getText().toString().replace(",", ".")));
                    //Variavel global do usuario
                    VariablesGlobal global = (VariablesGlobal) getApplication();
                    automovel.setIdUsuario(Integer.parseInt(global.getPessoa()));
                    CrudOperation crudOperation = new CrudOperation();
                    String retornoApi = crudOperation.cadastrarAutomovel(automovel);
                    if(retornoApi.equals("Sucesso")){
                        Toast.makeText(CadastroAutomovelActivity.this,"Automovel criado com sucesso.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadastroAutomovelActivity.this,LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CadastroAutomovelActivity.this,"Falha ao cadastrar automovel .",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroAutomovelActivity.this,"Preencha todos os campos.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCadastrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    btnCadastrar.setBackgroundColor(Color.rgb(0, 100, 0));
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    btnCadastrar.setBackgroundColor(getResources().getColor(R.color.text_background_verde));
                }
                return false;
            }
        });

    }
}
