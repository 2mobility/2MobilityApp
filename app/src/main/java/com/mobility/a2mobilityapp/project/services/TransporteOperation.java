package com.mobility.a2mobilityapp.project.services;

import com.mobility.a2mobilityapp.project.bean.Endereco;
import com.mobility.a2mobilityapp.project.bean.TransportePublico;
import com.mobility.a2mobilityapp.project.utils.HttpDataHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by limjo15 on 5/7/2018.
 */

public class TransporteOperation {

    private static final String API_KEY="AIzaSyDMJL4Q-GJD2FVVAb6gTgLtJsI7iIXTUos";

    public String getValuesTransport(Endereco endereco){
        HttpDataHandler http = new HttpDataHandler();
        try{
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+endereco.getLatitudePartida()+","+endereco.getLongitudePartida()
                    +"&destination="+endereco.getLatitudeChegada()+","+endereco.getLongitudeChegada()+"&mode=transit&key="+API_KEY;
            System.out.println(url+"/n");
            String response = http.getHttpData(url);
            System.out.println(response+"google");
            return response;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro 02 - Ao realizar operacao getValuesUber");
            return null;
        }
    }

    public TransportePublico getTransporte(String response){
        JSONObject jsonObject = null;
        TransportePublico transporte = new TransportePublico();
        try {
            jsonObject = new JSONObject(response);
            String valor = ((JSONArray)jsonObject.get("routes")).getJSONObject(0).getJSONObject("fare")
                    .get("value").toString();
            String distancia = ((JSONArray)jsonObject.get("routes")).getJSONObject(0).getJSONArray("legs")
                .getJSONObject(0).getJSONObject("distance").get("text").toString();
            String tempo = ((JSONArray)jsonObject.get("routes")).getJSONObject(0).getJSONArray("legs")
                .getJSONObject(0).getJSONObject("duration").get("text").toString();
            transporte.setPreco(valor);
            transporte.setDistancia(distancia);
            transporte.setTempo(tempo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return transporte;

    }

}
