package com.mobility.a2mobilityapp.project.model;

import java.util.Date;

public class Automovel {

	private int id;
	private int idUsuario;
	private String apelido;
	private double mediaCombustivel;
	private double kmLitro;
	private Date dataRegistro;
	
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
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public double getMediaCombustivel() {
		return mediaCombustivel;
	}
	public void setMediaCombustivel(double mediaCombustivel) {
		this.mediaCombustivel = mediaCombustivel;
	}
	public double getKmLitro() {
		return kmLitro;
	}
	public void setKmLitro(double kmLitro) {
		this.kmLitro = kmLitro;
	}
	public Date getDataRegistro() {
		return dataRegistro;
	}
	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
}
