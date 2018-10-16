package com.mobility.a2mobilityapp.project.bean;

/**
 * Created by limjo15 on 5/11/2018.
 */

public class MeioTransporte {

    private String nome;
    private String distancia;
    private String preco;
    private String tempo;
    private Integer imagem;
    private Integer id_usuario;

    public Integer getId_usuario(){
        return id_usuario;
    }
    public void setId_usuario(Integer id_usuario){ this.id_usuario = id_usuario;}
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

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

    public Integer getImagem() {
        return imagem;
    }

    public void setImagem(Integer imagem) {
        this.imagem = imagem;
    }
}
