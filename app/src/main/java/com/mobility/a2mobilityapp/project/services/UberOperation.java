package com.mobility.a2mobilityapp.project.services;

import com.google.gson.Gson;
import com.mobility.a2mobilityapp.project.bean.Endereco;
import com.mobility.a2mobilityapp.project.bean.Uber;
import com.mobility.a2mobilityapp.project.utils.HttpDataHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by limjo15 on 5/6/2018.
 */

public class UberOperation {

    private static final String SERVER_TOKEN = "zSDb9Z2xJ4V8HEczMfkhEEgfEn9afNiO-OkQJiTs";
    private static final String API_KEY="AIzaSyDMJL4Q-GJD2FVVAb6gTgLtJsI7iIXTUos";

    public String getProductsUber(float latitude, float longitude){
       HttpDataHandler http = new HttpDataHandler();
        try{

            String url = "https://api.uber.com/v1.2/products?latitude="+latitude+"&longitude="+longitude;
            String response = http.getHttpDataToken(url, SERVER_TOKEN);
            System.out.println(response);
            return response;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro 01 - Ao realizar operacao getProductsUber");
            return null;
        }
    }
    public String getValuesUber(Endereco endereco){
        HttpDataHandler http = new HttpDataHandler();
        try{
            String url = "https://api.uber.com/v1.2/estimates/price?start_latitude="+endereco.getLatitudePartida()+"&start_longitude="+endereco.getLongitudePartida()
                    +"&end_latitude="+endereco.getLatitudeChegada()+"&end_longitude="+endereco.getLongitudeChegada();
            String response = http.getHttpDataToken(url, SERVER_TOKEN);
            System.out.println(response);

            return response;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro 02 - Ao realizar operacao getValuesUber");
            return null;
        }
    }
    public Uber[] valoresUber(String json){
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        Uber[] uber = null;
        try {   
            jsonObject = new JSONObject(json);
            uber = gson.fromJson(jsonObject.getJSONArray("prices").toString(),Uber[].class);
            System.out.print(uber[1].getEstimate()+"OLAAAAAAA");
            return uber;
        } catch (JSONException e) {
            e.printStackTrace();
            return uber;
        }
    }
    public Uber[] produtosUber(String json){
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            Uber[] uber = gson.fromJson(jsonObject.getJSONArray("products").toString(),Uber[].class);
            return uber;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    public void chamaLatitudeLongitude(Endereco endereco){
        HttpDataHandler http = new HttpDataHandler();
        try{
            String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?key="+API_KEY+"&address=%s",endereco.getNominalPartida().replace(" ","+"));
            String response = http.getHttpData(url);
            JSONObject jsonObject = new JSONObject(response);
            String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lat").toString();
            String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lng").toString();
            endereco.setLongitudePartida(lng);
            endereco.setLatitudePartida(lat);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        try{
            String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?key="+API_KEY+"&address=%s",endereco.getNominalChegada().replace(" ","+"));
            String response = http.getHttpData(url);
            JSONObject jsonObject = new JSONObject(response);
            String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lat").toString();
            String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lng").toString();
            endereco.setLongitudeChegada(lng);
            endereco.setLatitudeChegada(lat);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
