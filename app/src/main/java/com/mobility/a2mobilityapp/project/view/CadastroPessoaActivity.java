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
import android.widget.Switch;
import android.widget.Toast;

import com.mobility.a2mobilityapp.LoginActivity;
import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.model.Usuario;
import com.mobility.a2mobilityapp.project.services.CrudOperation;
import com.mobility.a2mobilityapp.project.utils.Criptografia;
import com.mobility.a2mobilityapp.project.utils.Mask;
import com.mobility.a2mobilityapp.project.utils.ValidadorCPF;
import com.mobility.a2mobilityapp.project.utils.ValidadorEmail;
import com.mobility.a2mobilityapp.project.utils.ValidadorSenha;
import com.mobility.a2mobilityapp.project.utils.VariablesGlobal;

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
    private ValidadorSenha validadorSenha = new ValidadorSenha();
    private ValidadorEmail validadorEmail = new ValidadorEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);
        //Workaround
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
                if(!campoNome.getText().toString().equals("") && !campoCelular.getText().toString().equals("") &&
                        !campoData.getText().toString().equals("") && !campoCpf.getText().toString().equals("") &&
                        !campoEmail.getText().toString().equals("") && !campoSenha.getText().toString().equals("") &&
                        !campoConfirmarSenha.getText().toString().equals("")){
                    //trata CPF
                    String cpf = campoCpf.getText().toString().replace(".","").replace("-","");
                    if(validadorCpf.isCPF(cpf)){
                        //verificar email
                        if(validadorEmail.isEmail(campoEmail.getText().toString())){
                            //Verificar se senha esta nos padrões Exemplo@123
                            if(validadorSenha.isSenha(campoSenha.getText().toString())){
                                // tratar a senha iguais
                                if(campoSenha.getText().toString().equals(campoConfirmarSenha.getText().toString())){
                                    String senha = criptografia.criptografar(campoSenha.getText().toString());
                                    String confirmSenha = criptografia.criptografar(campoConfirmarSenha.getText().toString());
                                    try{
                                        Usuario usuario = new Usuario();
                                        String ddd = campoCelular.getText().toString().substring(1,3);
                                        String telefone = campoCelular.getText().toString().substring(4);
                                        usuario.setDdd(ddd);
                                        usuario.setTelefone(telefone.replace("-",""));
                                        usuario.setCpf(cpf);
                                        usuario.setNome(campoNome.getText().toString());
                                        usuario.setEmail(campoEmail.getText().toString());
                                        usuario.setSenha(senha);
                                        CrudOperation crud = new CrudOperation();
                                        String resultado = crud.cadastrarPessoa(usuario);
                                        Boolean status = switchId.isChecked();
                                        final VariablesGlobal variablesGlobal = (VariablesGlobal) getApplicationContext();
                                        String[] resultadoParse = resultado.split(":");
                                        if(resultadoParse[0].equals("ID")){
                                            variablesGlobal.setPessoa(resultadoParse[1]);
                                            Toast.makeText(CadastroPessoaActivity.this,"Conta criada com sucesso.",Toast.LENGTH_SHORT).show();
                                            if (status == true) {
                                                //setContentView(R.layout.fragment_cadastro_automovel);
                                                Intent intent = new Intent(CadastroPessoaActivity.this,CadastroAutomovelActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(CadastroPessoaActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }else {
                                            Toast.makeText(CadastroPessoaActivity.this,"Falha no provisionamento de conta.",Toast.LENGTH_SHORT).show();
                                        }



                                    } catch(Exception e){
                                        Toast.makeText(CadastroPessoaActivity.this,"Falha ao criar a conta.",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(CadastroPessoaActivity.this,"As senhas não coincidem.",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(CadastroPessoaActivity.this,"Senha Inválida! Ex: \"Exemplo@123\"",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CadastroPessoaActivity.this,"E-mail inválido. Forneça um e-mail existente.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroPessoaActivity.this,"CPF inválido.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroPessoaActivity.this,"Atenção! Existem campos a serem preenchidos.",Toast.LENGTH_SHORT).show();
                }
                //checando os status (true / false)
                Boolean status = switchId.isChecked();
                if (status == true) {
                    setContentView(R.layout.fragment_cadastro_automovel);
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
