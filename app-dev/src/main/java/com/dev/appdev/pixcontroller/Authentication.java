package com.dev.appdev.pixcontroller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.json.JSONObject;


public class Authentication {
	
	public final String CLIENTE_ID = "Client_Id_seu id";
	public final String CLIENTE_SECRET = "Client_seu secret";
	//public final String P12 = "producao-314898-api.p12";
	private final String BASICAUTH = Base64.getEncoder().encodeToString(((CLIENTE_ID+':'+CLIENTE_SECRET).getBytes()));

	/**
	 * Pode retornar todo corpo JSON após a consulta dos dados cadastrais, mas nesse
	 * modele estamos retrnando apenas o token de acesso
	 * 
	 * @return o token de autenticação
	 */
	public String generateToken() {
		String access_token = "";
		try {
			System.setProperty("javax.net.ssl.keyStore", "producao-314898-api.p12");
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

			URL url = new URL("https://api-pix.gerencianet.com.br/oauth/token");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + BASICAUTH);
			conn.setSSLSocketFactory(sslsocketfactory);
			
			String input = "{\"grant_type\": \"client_credentials\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(reader);

			String response;
			StringBuilder responseBuilder = new StringBuilder();
			while ((response = br.readLine()) != null) {
				// System.out.println(response);
				responseBuilder.append(response);
			}
			try {
				JSONObject jsonObject = new JSONObject(responseBuilder.toString());
				access_token = jsonObject.getString("access_token");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Erro: generateToken() " + responseBuilder);
				e.printStackTrace();
			}
			conn.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro: autenticacao ");
			e.printStackTrace();
		}

		return access_token;

	}

}
