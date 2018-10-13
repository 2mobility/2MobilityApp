package com.mobility.a2mobilityapp.project.services;

import com.mobility.a2mobilityapp.project.model.Automovel;
import com.mobility.a2mobilityapp.project.model.Usuario;
import com.mobility.a2mobilityapp.project.model.Viagem;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class CrudOperation {

	private static final String url = "";
	
	/*
	 * Cadastro Pessoa
	 * CPF
	 * EMAIL
	 * NOME COMPLETO
	 * TELEFONE
	 * DDD
	 * SENHA
	 * DATA NASCIMENTO
	 * DATA CRIAÇÃO
	 */
	public void cadastrarPessoa(Usuario usuario) {
		try {
			URL urlCadastroPessoa = new URL("http://"+ url +":8080/cadastrarPessoa");
			HttpURLConnection conn = (HttpURLConnection)urlCadastroPessoa.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			
			String json = "{\"cpf\":\"" + usuario.getCpf() + "\"," +
							"\"email\":\"" + usuario.getEmail() + "\"," +
							"\"nome_completo\":\"" + usuario.getNome() + "\"," +
							"\"telefone\":\"" + usuario.getTelefone() + "\"," +
							"\"ddd\":\"" + usuario.getDdd() + "\"," +
							"\"senha\":\"" + usuario.getSenha() + "\"," +
							"\"dt_nascimento\":\"" + usuario.getDataNascimento() + "\"," +
							"\"dt_criacao\":\"" + usuario.getDataCricao() + "\"}";
			
			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			
			int responseCodeLogin = conn.getResponseCode();
            if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
            	System.out.println("Cadastro Efetuado com Sucesso...");
            }else {
            	System.out.println("Erro no Cadastro...");
            }
            
            conn.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Cadastro Automovel
	 * APELIDO
	 * MEDIA COMBUSTIVEL
	 * KM POR LITRO
	 * DATA REGISTRO
	 */
	public void cadastrarAutomovel(Automovel automovel) {
		try {
			URL urlCadastroAutomovel = new URL("http://"+ url +":8080/cadastrarAutomovel");
			HttpURLConnection conn = (HttpURLConnection)urlCadastroAutomovel.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			
			String json = "{\"apelido\":\"" + automovel.getApelido() + "\"," +
							"\"media_combustivel\":\"" + automovel.getMediaCombustivel() + "\"," +
							"\"km_litro\":\"" + automovel + "\"," +
							"\"dt_registro\":\"" + automovel.getDataRegistro() + "\"}";
			
			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			
			int responseCodeLogin = conn.getResponseCode();
            if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
            	System.out.println("Cadastro Efetuado com Sucesso...");
            }else {
            	System.out.println("Erro no Cadastro...");
            }
            
            conn.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Cadastro Viagem
	 * TEMPO VIAGEM
	 * DISTANCIA VIAGEM
	 * ENDEREÇO DE PARTIDA
	 * ENDEREÇO DE CHEGADA
	 * DATA VIAGEM
	 * ID DO USUARIO
	 * ID TIPO DO TRANSPORTE
	 */
	public void cadastrarViagem(Viagem viagem) {
		try {
			URL urlCadastroViagem = new URL("http://"+ url +":8080/cadastrarViagem");
			HttpURLConnection conn = (HttpURLConnection)urlCadastroViagem.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			
			String json = "{\"tempo_viagem\":\"" + viagem.getTempoViagem() + "\"," +
							"\"distancia_viagem\":\"" + viagem.getDistanciaViagem() + "\"," +
							"\"endereco_partida\":\"" + viagem.getEnderecoPartida() + "\"," +
							"\"endereco_chegada\":\"" + viagem.getEnderecoDestino() + "\"," +
							"\"date_viagem\":\"" + viagem.getDataViagem() + "\"," +
							"\"id_usuario\":\"" + viagem.getIdUsuario() + "\"," +
							"\"id_tipo_transporte\":\"" + viagem.getIdTransporte() + "\"}";
			
			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			
			int responseCodeLogin = conn.getResponseCode();
            if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
            	System.out.println("Cadastro Efetuado com Sucesso...");
            }else {
            	System.out.println("Erro no Cadastro...");
            }
            
            conn.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Login
	 * USERNAME
	 * SENHA
	 */
	public void login(String login, String senha) {
		try {
			URL urlLogin = new URL("http://"+ url +":8080/login");
			HttpURLConnection conn = (HttpURLConnection)urlLogin.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			
			String json = "{\"username\":\"" + login + "\"," +
							"\"password\":\"" + senha + "\"}";
			
			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			
			int responseCodeLogin = conn.getResponseCode();
            if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
            	System.out.println("Login Efetuado com Sucesso...");
            }else {
            	System.out.println("Login Rejeitado...");
            }
            
            conn.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Retorna Tipo Automovel
	 * TIPO DO TRANSPORTE
	 * APELIDO
	 */
	public void retornaTipoAutomovel(String tipoTransporte, String apelido) {
		try {
			URL urlRetornaTipoAutomovel = new URL("http://"+ url +":8080/retornaTipoAutomovel");
			HttpURLConnection conn = (HttpURLConnection)urlRetornaTipoAutomovel.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			
			String json = "{\"tipo_transporte\":\"" + tipoTransporte + "\"," +
							"\"apelido\":\"" + apelido + "\"}";
			
			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			
			int responseCodeLogin = conn.getResponseCode();
            if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
            	System.out.println("Automovel Retornado com Sucesso...");
            }else {
            	System.out.println("Erro em Retornar Automovel...");
            }
            
            conn.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Retorna Viagem por ID
	 * ID DO USUARIO
	 */
	public void retornaViagemPorID(String id) {
		try {
			URL urlRetornaViagemPorID = new URL("http://"+ url +":8080/retornaViagemporID");
			HttpURLConnection conn = (HttpURLConnection)urlRetornaViagemPorID.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			
			String json = "{\"id_usuario\":\"" + id + "\"}";
			
			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			
			int responseCodeLogin = conn.getResponseCode();
            if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
            	System.out.println("Viagem por ID Retornada com Sucesso...");
            }else {
            	System.out.println("Erro em Retornar Viagem por ID...");
            }
            
            conn.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Retorna Viagem por Tempo
	 * ID DO USUARIO
	 * DATA INICIO BUSCA
	 * DATA FIM BUSCA
	 */
	public void retornaViagemPorTempo(String id, String dataInicio, String dataFim) {
		try {
			URL urlRetornaViagemPorID = new URL("http://"+ url +":8080/retornaViagemporTempo");
			HttpURLConnection conn = (HttpURLConnection)urlRetornaViagemPorID.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			
			String json = "{\"id_usuario\":\"" + id + "\"," +
					"\"data_inicio_busca\":\"" + dataInicio + "\"," +
					"\"data_fim_busca\":\"" + dataFim + "\"}";
			
			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			
			int responseCodeLogin = conn.getResponseCode();
            if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
            	System.out.println("Viagem por Tempo Retornada com Sucesso...");
            }else {
            	System.out.println("Erro em Retornar Viagem por Tempo...");
            }
            
            conn.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
