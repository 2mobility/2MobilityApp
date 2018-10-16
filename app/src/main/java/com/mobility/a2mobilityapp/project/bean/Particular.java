package com.mobility.a2mobilityapp.project.bean;

public class Particular {
    private String preco;
    private String tempo;
    private String distancia;
    private String kmLitro;

    public String getKmLitro() {
        return kmLitro;
    }

    public void setKmLitro(String kmLitro) {
        this.kmLitro = kmLitro;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    private String apelido;

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }
}
