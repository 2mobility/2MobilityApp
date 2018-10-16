package com.mobility.a2mobilityapp.project.services;

import com.mobility.a2mobilityapp.project.bean.MeioTransporte;
import com.mobility.a2mobilityapp.project.bean.Particular;
import com.mobility.a2mobilityapp.project.model.Automovel;
import com.mobility.a2mobilityapp.project.model.Usuario;
import com.mobility.a2mobilityapp.project.model.Viagem;
import com.mobility.a2mobilityapp.project.utils.VariablesGlobal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;


public class CrudOperation {
		private static final String url = "192.168.130.1";

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
	public String cadastrarPessoa(Usuario usuario) {
		try {
			URL urlCadastroPessoa = new URL("http://"+ url +":8012/2mobility/createUser");
			HttpURLConnection conn = (HttpURLConnection)urlCadastroPessoa.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("Accept", "application/json");

			String json = "{\"cpf\":\"" + usuario.getCpf() + "\"," +
					"\"email\":\"" + usuario.getEmail() + "\"," +
					"\"nome_completo\":\"" + usuario.getNome() + "\"," +
					"\"telefone\":\"" + usuario.getTelefone() + "\"," +
					"\"ddd\":\"" + usuario.getDdd() + "\"," +
					"\"senha\":\"" + usuario.getSenha() + "\"}" ;
			//"\"dt_nascimento\":\"" + usuario.getDataNascimento() + "\"," +
			//"\"dt_criacao\":\"" + usuario.getDataCricao() + "\"}";

			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			String result = null;
			StringBuffer sb = new StringBuffer();
			InputStream is = null;
			try {
				is = new BufferedInputStream(conn.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String inputLine = "";
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}
				result = sb.toString();
			} catch( Exception e){
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject(result);
			String retorno = jsonObject.getString("ID");
			int responseCodeLogin = conn.getResponseCode();
			if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
				conn.disconnect();
				return "ID:"+retorno;
			}else {
				return "Falha";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "Falha";
		}
	}
	/*
		Retorna Transporte privado
	 */
	public Automovel retornaParticular(String user) {
		Automovel particular = new Automovel();
		try {
			URL urlCadastroPessoa = new URL("http://"+ url +":8012/2mobility/retornaParticular");
			HttpURLConnection conn = (HttpURLConnection)urlCadastroPessoa.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("Accept", "application/json");

			String json = "{\"id\":\"" + user + "\"}";

			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			String result = null;
			StringBuffer sb = new StringBuffer();
			InputStream is = null;
			try {
				is = new BufferedInputStream(conn.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String inputLine = "";
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}
				result = sb.toString();
			} catch( Exception e){
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject(result);

			int responseCodeLogin = conn.getResponseCode();
			if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
				conn.disconnect();
				String apelido = jsonObject.getString("apelido");
				String media_combustivel = jsonObject.getString("media_combustivel");
				String km_litro = jsonObject.getString("km_litro");
				particular.setApelido(apelido);
				particular.setMediaCombustivel(Double.parseDouble(media_combustivel));
				particular.setKmLitro(Double.parseDouble(km_litro));
				return particular;
			}else {
				return particular;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return particular;
		}
	}

	/*
	 * Cadastro Automovel
	 * APELID
	 * MEDIA COMBUSTIVEL
	 * KM POR LITRO
	 * DATA REGISTRO
	 */
	public String cadastrarAutomovel(Automovel automovel) {
		try {
			URL urlCadastroAutomovel = new URL("http://"+ url +":8012/2mobility/cadastrarAutomovel");
			HttpURLConnection conn = (HttpURLConnection)urlCadastroAutomovel.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");

			String json = "{\"apelido\":\"" + automovel.getApelido() + "\"," +
					"\"media_combustivel\":\"" + automovel.getMediaCombustivel() + "\"," +
					"\"km_litro\":\"" + automovel.getKmLitro() + "\"," +
					"\"id_pessoa\":\"" + automovel.getIdUsuario() + "\"," +
					"\"dt_registro\":\"\"}";

			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();

			int responseCodeLogin = conn.getResponseCode();

			if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
				conn.disconnect();
				return "Sucesso";
			}else {
				conn.disconnect();
				return "Falha";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "Falha";
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
	public void cadastrarViagem(MeioTransporte viagem) {
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		try {
			URL urlCadastroViagem = new URL("http://"+ url +":8012/2mobility/cadastrarViagem");
			HttpURLConnection conn = (HttpURLConnection)urlCadastroViagem.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");

			String json = "{\"tempo_viagem\":\"" + viagem.getTempo().replace(" mins","") + "\"," +
					"\"preco_viagem\":\"" + viagem.getPreco().replace("R$","") + "\"," +
					"\"date_viagem\":\"" + modifiedDate + "\"," +
                    "\"apelido\":\"" + viagem.getNome()+ "\"," +
					"\"id_usuario\":\"" + viagem.getId_usuario() + "\"}";

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
	 * Serviço Login
	 * USERNAME
	 * SENHA
	 */
	public String login(String login, String senha) {
		try {
			URL urlLogin = new URL("http://"+ url +":8012/2mobility/login");
			HttpURLConnection conn = (HttpURLConnection)urlLogin.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");

			String json = "{\"email\":\"" + login + "\"," +
					"\"senha\":\"" + senha + "\"}";

			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			String result = null;
			StringBuffer sb = new StringBuffer();
			InputStream is = null;
			try {
				is = new BufferedInputStream(conn.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String inputLine = "";
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}
				result = sb.toString();
			} catch( Exception e){
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject(result);
			int responseCodeLogin = conn.getResponseCode();
			if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
				conn.disconnect();
				String retorno = jsonObject.getString("ID");
				return retorno;
			}else {
				conn.disconnect();
				return "Falha";
			}

		}catch(Exception e) {
			e.printStackTrace();
			return "Falha";
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
	/*public String resetSenha(Usuario usuario) {
		try {
			URL urlLogin = new URL("http://"+ url +":8012/2mobility/login");
			HttpURLConnection conn = (HttpURLConnection)urlLogin.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");

			String json = "{\"email\":\"" + login + "\"," +
					"\"senha\":\"" + senha + "\"}";

			OutputStream osLogin = conn.getOutputStream();
			osLogin.write(json.getBytes());
			osLogin.flush();
			String result = null;
			StringBuffer sb = new StringBuffer();
			InputStream is = null;
			try {
				is = new BufferedInputStream(conn.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String inputLine = "";
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}
				result = sb.toString();
			} catch( Exception e){
				e.printStackTrace();
			}
			JSONObject jsonObject = new JSONObject(result);
			int responseCodeLogin = conn.getResponseCode();
			if(responseCodeLogin == HttpsURLConnection.HTTP_OK){
				conn.disconnect();
				String retorno = jsonObject.getString("ID");
				return retorno;
			}else {
				conn.disconnect();
				return "Falha";
			}

		}catch(Exception e) {
			e.printStackTrace();
			return "Falha";
		}
	}*/
}
