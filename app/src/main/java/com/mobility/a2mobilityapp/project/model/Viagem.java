package com.mobility.a2mobilityapp.project.model;

import java.util.Date;

public class Viagem {

	private int id;
	private int idUsuario;
	private int idTransporte;
	private String tempoViagem;
	private String distanciaViagem;
	private String enderecoPartida;
	private String enderecoDestino;
	private Date dataViagem;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdTransporte() {
		return idTransporte;
	}
	public void setIdTransporte(int idTransporte) {
		this.idTransporte = idTransporte;
	}
	public String getTempoViagem() {
		return tempoViagem;
	}
	public void setTempoViagem(String tempoViagem) {
		this.tempoViagem = tempoViagem;
	}
	public String getDistanciaViagem() {
		return distanciaViagem;
	}
	public void setDistanciaViagem(String distanciaViagem) {
		this.distanciaViagem = distanciaViagem;
	}
	public String getEnderecoPartida() {
		return enderecoPartida;
	}
	public void setEnderecoPartida(String enderecoPartida) {
		this.enderecoPartida = enderecoPartida;
	}
	public String getEnderecoDestino() {
		return enderecoDestino;
	}
	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}
	public Date getDataViagem() {
		return dataViagem;
	}
	public void setDataViagem(Date dataViagem) {
		this.dataViagem = dataViagem;
	}
}
