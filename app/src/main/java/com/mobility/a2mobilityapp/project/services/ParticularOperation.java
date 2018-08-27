package com.mobility.a2mobilityapp.project.services;

import com.mobility.a2mobilityapp.project.bean.Particular;

public class ParticularOperation {

    public Particular getParticular(){
        Particular particular = new Particular();

        particular.setPreco("20,00");
        particular.setDistancia("20");
        particular.setTempo("30");

        return particular;
    }
}
