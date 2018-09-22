package com.mobility.a2mobilityapp.project.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.utils.Criptografia;
import com.mobility.a2mobilityapp.project.utils.Mask;
import com.mobility.a2mobilityapp.project.utils.ValidadorCPF;

public class SenhaActivity extends AppCompatActivity {

    private EditText campoCpf;
    private EditText campoData;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText campoConfirmarSenha;
    private Button btnConcluir;
    private ValidadorCPF validadorCpf = new ValidadorCPF();
    private Criptografia criptografia = new Criptografia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        campoCpf = (EditText) findViewById(R.id.editText14);
        campoCpf.addTextChangedListener(Mask.mask(campoCpf, Mask.FORMAT_CPF));
        campoData = (EditText) findViewById(R.id.editText);
        campoData.addTextChangedListener(Mask.mask(campoData, Mask.FORMAT_DATE));
        campoEmail = (EditText) findViewById(R.id.editText15);
        campoSenha = (EditText) findViewById(R.id.editText16);
        campoConfirmarSenha = (EditText) findViewById(R.id.editText17);
        btnConcluir = (Button) findViewById(R.id.button);

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // trata os campos vazios
                if(!campoData.getText().equals("") && !campoEmail.getText().equals("") && !campoCpf.getText().equals("") &&
                        !campoSenha.getText().equals("") && !campoConfirmarSenha.getText().toString().equals("")){
                    //trata CPF
                    String cpf = campoCpf.getText().toString().replace(".","").replace("-","");
                    if(validadorCpf.isCPF(cpf)){
                        // tratar a senha
                        if(campoSenha.getText().toString().equals(campoConfirmarSenha.getText().toString())){
                            String senha = criptografia.criptografar(campoSenha.getText().toString());
                            String confirmSenha = criptografia.criptografar(campoConfirmarSenha.getText().toString());
                            /*
                            Mandar para api
                             */
                        }else{
                            Toast.makeText(SenhaActivity.this,"As senhas são diferentes.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SenhaActivity.this,"CPF inválido.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SenhaActivity.this,"Atenção! Preencha todos os campos.",Toast.LENGTH_SHORT).show();

                }
            }
        });

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

    }
}
