package com.mobility.a2mobilityapp.project.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.utils.Criptografia;
import com.mobility.a2mobilityapp.project.utils.Mask;
import com.mobility.a2mobilityapp.project.utils.ValidadorCPF;

public class CadastroPessoaActivity extends AppCompatActivity {

    private EditText campoCpf;
    private EditText campoCelular;
    private EditText campoData;
    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoSenha;
    private EditText campoConfirmarSenha;
    private Button btnConcluir;
    private Switch switchId;
    private ValidadorCPF validadorCpf = new ValidadorCPF();
    private Criptografia criptografia = new Criptografia();

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
        campoNome = (EditText) findViewById(R.id.editText);
        campoEmail = (EditText) findViewById(R.id.editText5);
        campoSenha = (EditText) findViewById(R.id.editText6);
        campoConfirmarSenha = (EditText) findViewById(R.id.editText8);
        btnConcluir = (Button) findViewById(R.id.button);
        switchId = (Switch) findViewById(R.id.switch2);

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // trata os campos vazios
                if(!campoNome.getText().equals("") && !campoCelular.getText().equals("") && !campoData.getText().equals("") &&
                        !campoCpf.getText().equals("") && !campoEmail.getText().equals("") &&
                        !campoSenha.getText().equals("") && !campoConfirmarSenha.getText().equals("")){
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
                            Toast.makeText(CadastroPessoaActivity.this,"As senhas são diferentes.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroPessoaActivity.this,"CPF inválido.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroPessoaActivity.this,"Atenção! Preencha todos os campos.",Toast.LENGTH_SHORT).show();

                }
                //checando os status (true / false)
                Boolean status = switchId.isChecked();
                if (status == true) {
                    setContentView(R.layout.fragment_cadastro_automovel);
                }
            }
        });
    }
}
