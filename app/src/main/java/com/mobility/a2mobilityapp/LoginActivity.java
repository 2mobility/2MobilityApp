package com.mobility.a2mobilityapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mobility.a2mobilityapp.project.view.CadastroPessoaActivity;
import com.mobility.a2mobilityapp.project.view.SenhaActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int ERRO_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isServiceOK()){
            init();
        }

        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Cadastrar! Construindo...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, CadastroPessoaActivity.class);
                startActivity(intent);
            }
        });

        TextView txtEsqueceuSenha = (TextView) findViewById(R.id.txtEsqueceuSenha);
        txtEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SenhaActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Esqueceu a senha! Construindo...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void init(){
        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServiceOK(){
        Log.d(TAG, "isServiceOK: Checando versão dos serviços do Google");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(LoginActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //conectado ao Google com sucesso e é possível executar requests
            Log.d(TAG, "isServiceOK: Serviço Google Play está funcionando");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //ocorreu um erro possível de resolver
            Log.d(TAG, "isServiceOK: Ocorreu um erro possível de ser resolvido");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(LoginActivity.this, available, ERRO_DIALOG_REQUEST);
            dialog.show();
        } else{
            Toast.makeText(this, "Você não pode executar requests", Toast.LENGTH_SHORT);
        }
        return false;
    }
}
