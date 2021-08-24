package com.dev.appdev.pixcontroller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.dev.appdev.model.Usuario;
import com.mifmif.common.regex.Generex;

public class Cobranca {
	
	/**
	 * QUALQUER ALTERAÇÃO NESSE TRECHO DE CODIGO PODE GERAR ERRO
	 * esse gerador e pix e para o usuaio do sistema pagar, entao puxa os dados dele como devedor
	 * @param token
	 * @return uma cobranca com as informaços para onde vai o pagamento
	 */
	public String realizaCobranca(String token, String valor) {
		
		Usuario usuario = new Usuario(null,"03423298096", "Pedro", "Rua X", 100.00);
		
		
		String payload = "{\r\n"
				+ "  \"calendario\": {\r\n"
				+ "    \"expiracao\": 3600\r\n"
				+ "  },\r\n"
				+ "  \"devedor\": {\r\n"
				+ "    \"cnpj\": \"59491686000103\",\r\n"
				+ "    \"nome\":  "+"\""+usuario.getNome()+"\""+"\r\n"
				+ "  },\r\n"
				+ "  \"valor\": {\r\n"
				+ "    \"original\": "+"\""+valor+"\""+"\r\n"
				+ "  },\r\n"
				+ "  \"chave\": \"AQUI VAI A CHAVE\",\r\n"
				+ "  \"solicitacaoPagador\": \"Informe o número ou identificador do pedido.\",\r\n"
				+ "  \"infoAdicionais\": [\r\n"
				+ "    {\r\n"
				+ "      \"nome\": \"Campo info\",\r\n"
				+ "      \"valor\": \"info\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"nome\": \"Campo info\",\r\n"
				+ "      \"valor\": \"info\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";


		System.out.println(payload);

		StringBuilder responseBuilder = new StringBuilder();
		HttpsURLConnection conn = null;

		try {
			System.setProperty("javax.net.ssl.keyStore", "producao-314898-api.p12");
			
			//O campo txid determina o identificador da transação
			String txid;
			Generex generex = new Generex("[a-zA-Z0-9]{26,35}");
			txid = generex.random();
			
			//Endpoint para cadastrar uma cobrança com um identificador de transação (txid).
			URL url = new URL("https://api-pix.gerencianet.com.br/v2/cob/" + txid);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + token);

			OutputStream os = conn.getOutputStream();
			os.write(payload.getBytes());
			os.flush();

			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(reader);

			String response;
			while ((response = br.readLine()) != null) {
				responseBuilder.append(response);
			}
		} catch (Exception e) {
			System.out.println("Erro! (realizaCobranca) Erro ao gerar cobranca");
			e.printStackTrace();
		}
		return responseBuilder.toString();
	}

	/**
	 * 
	 * @param cobrança
	 * @return numero identificador da cobrança
	 */
	public int getIdCobranca(String cobranca) {
		int id = 0;
		try {
			JSONObject jsonObject = new JSONObject(cobranca);
			JSONObject loc = (JSONObject) jsonObject.get("loc");
			id = loc.getInt("id");
		} catch (Exception e) {
			System.out.println("Erro! (getIdCobranca) id da cobranca sem identificacao");
			e.printStackTrace();
		}
		return id;
	}

}
