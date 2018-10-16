package com.mobility.a2mobilityapp.project.services;

import com.mobility.a2mobilityapp.project.bean.MeioTransporte;
import com.mobility.a2mobilityapp.project.bean.Particular;
import com.mobility.a2mobilityapp.project.bean.Uber;

public class ParticularOperation {

    private static Uber[] uber;

    public Particular getParticular(String user){
        Particular particular = new Particular();

        /*if(uber != null){
            for(int i=0;i<uber.length-1;i++){
                MeioTransporte transporte = new MeioTransporte();
                transporte.setDistancia(Float.toString(uber[i].getDistance()) + " km");
                transporte.setNome(uber[i].getDisplay_name());
                transporte.setPreco(uber[i].getEstimate());
                int tempoUber = uber[i].getDuration() / 60;
                transporte.setTempo(tempoUber + " mins");
                listaMeios.add(transporte);
            }
        }*/

        particular.setPreco("20,00");
        //particular.setDistancia(Float.toString(uber[1].getDistance()));
        particular.setTempo("30");

       /* particular.setPreco("20,00");
        particular.setDistancia("20");
        particular.setTempo("30");*/

        return particular;
    }
}
