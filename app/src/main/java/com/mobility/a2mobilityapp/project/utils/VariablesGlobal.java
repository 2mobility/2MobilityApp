package com.mobility.a2mobilityapp.project.utils;

import android.app.Application;

public class VariablesGlobal extends Application {
    private String idPessoa = "0";

    public String getPessoa(){
        return this.idPessoa;
    }

    public void setPessoa(String idPessoa){
        this.idPessoa = idPessoa;
    }
}
