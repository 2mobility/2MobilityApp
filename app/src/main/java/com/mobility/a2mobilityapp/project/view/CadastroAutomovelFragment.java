package com.mobility.a2mobilityapp.project.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobility.a2mobilityapp.LoginActivity;
import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.model.Automovel;
import com.mobility.a2mobilityapp.project.services.CrudOperation;
import com.mobility.a2mobilityapp.project.utils.VariablesGlobal;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroAutomovelFragment extends AppCompatActivity {

    Button btnCadastrar;

    public CadastroAutomovelFragment() {
        // Required empty public constructor
    }
    private EditText modelo;
    private EditText litro;
    private EditText preco;
    /*View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cadastro_automovel, container, false);

        btnCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        modelo = (EditText) view.findViewById(R.id.editText);
        litro = (EditText) view.findViewById(R.id.editText13);
        preco = (EditText) view.findViewById(R.id.editText18);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!modelo.getText().toString().equals("") && !litro.getText().equals("") && !preco.getText().toString().equals("")) {
                    Automovel automovel = new Automovel();
                    automovel.setApelido(modelo.getText().toString());
                    automovel.setMediaCombustivel(Double.parseDouble(preco.getText().toString().replace(",", ".")));
                    automovel.setKmLitro(Double.parseDouble(litro.getText().toString().replace(",", ".")));
                    //Variavel global do usuario
                    VariablesGlobal global = (VariablesGlobal) getActivity().getApplication();
                    automovel.setIdUsuario(Integer.parseInt(global.getPessoa()));
                    CrudOperation crudOperation = new CrudOperation();
                    String retornoApi = crudOperation.cadastrarAutomovel(automovel);
                    if(retornoApi.equals("Sucesso")){
                        Toast.makeText(getContext(),"Automovel criado com sucesso.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getContext(),"Preencha todos os campos.",Toast.LENGTH_SHORT).show();
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

        return view;

    }*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cadastro_automovel);
        btnCadastrar = (Button) findViewById(R.id.buttonCadastrar);
        modelo = (EditText) findViewById(R.id.editText);
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
                        Toast.makeText(CadastroAutomovelFragment.this,"Automovel criado com sucesso.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadastroAutomovelFragment.this,LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(CadastroAutomovelFragment.this,"Preencha todos os campos.",Toast.LENGTH_SHORT).show();
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
