package com.mobility.a2mobilityapp.project.view;

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

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.model.Usuario;
import com.mobility.a2mobilityapp.project.utils.Criptografia;
import com.mobility.a2mobilityapp.project.utils.Mask;
import com.mobility.a2mobilityapp.project.utils.ValidadorCPF;
import com.mobility.a2mobilityapp.project.utils.ValidadorEmail;
import com.mobility.a2mobilityapp.project.utils.ValidadorSenha;

public class SenhaActivity extends AppCompatActivity {

    private EditText campoCpf;
    private EditText campoData;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText campoConfirmarSenha;
    private Button btnConcluir;
    private ValidadorCPF validadorCpf = new ValidadorCPF();
    private Criptografia criptografia = new Criptografia();
    private ValidadorSenha validadorSenha = new ValidadorSenha();
    private ValidadorEmail validadorEmail = new ValidadorEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
                if(!campoData.getText().toString().equals("") && !campoEmail.getText().toString().equals("") &&
                        !campoCpf.getText().toString().equals("") && !campoSenha.getText().toString().equals("") &&
                        !campoConfirmarSenha.getText().toString().equals("")){
                    //trata CPF
                    String cpf = campoCpf.getText().toString().replace(".","").replace("-","");
                    if(validadorCpf.isCPF(cpf)){
                        //verificar email
                        if(validadorEmail.isEmail(campoEmail.getText().toString())){
                            //Verificar se senha esta nos padrões Exemplo@123
                            if(validadorSenha.isSenha(campoSenha.getText().toString())){
                                // tratar a senha
                                if(campoSenha.getText().toString().equals(campoConfirmarSenha.getText().toString())){
                                    String senha = criptografia.criptografar(campoSenha.getText().toString());
                                    String confirmSenha = criptografia.criptografar(campoConfirmarSenha.getText().toString());

                                    Usuario usuario = new Usuario();
                                    usuario.setEmail(campoEmail.getText().toString());
                                    usuario.setDataNascimento(campoData.getText().toString());
                                }else{
                                    Toast.makeText(SenhaActivity.this,"As senhas não coincidem.",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(SenhaActivity.this,"Senha Inválida! Ex: \"Exemplo@123\"",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SenhaActivity.this,"E-mail inválido. Forneça um e-mail existente.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SenhaActivity.this,"CPF inválido.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SenhaActivity.this,"Atenção! Existem campos a serem preenchidos.",Toast.LENGTH_SHORT).show();

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
