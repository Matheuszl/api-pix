package com.dev.appdev.pixcontroller;


import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class QRcode {
	
	private String qRCode;
	private String image;
	
	/**
	 * Metodo que gera as informaçoes presentes no Qrcode
	 * @param idCobranca
	 * @param access_token
	 * @return qrcode
	 */
	public String geradorQrCode(int idCobranca, String access_token) {
		String resultado = "";
		StringBuilder responseBuilder = new StringBuilder();
		HttpsURLConnection conn = null;
		try {
			URL url = new URL("https://api-pix.gerencianet.com.br/v2/loc/" + idCobranca + "/qrcode"); 
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + access_token);

			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(reader);

			String response;
			while ((response = br.readLine()) != null) {
				responseBuilder.append(response);
			}
			resultado = responseBuilder.toString();
		} catch (Exception e) {
			System.out.println("Erro na geração do QrCode");
			e.printStackTrace();
		}

		return resultado;

	}

	/**
	 * 
	 * @param loc
	 * @return qrCode em formato de string
	 */
	public String gerarQrCode(String loc) {
		try {
			JSONObject jsonObject = new JSONObject(loc);
			this.qRCode = jsonObject.getString("qrcode");
		} catch (Exception e) {
			System.out.println("Erro! (gerarQrCode)");
			e.printStackTrace();
		}
		return this.qRCode;
	}

	/**
	 * 
	 * @param recebe o codigo do qrcode
	 * @return a imagem qrcode 
	 */
	public String gerarImagemQrCod(String responseQR) {
		try {
			JSONObject jsonObject = new JSONObject(responseQR);
			this.image = jsonObject.getString("imagemQrcode");
		} catch (Exception e) {
			System.out.println("Erro! (getImagem)");
			e.printStackTrace();
		}
		return this.image; 
	}
	
	/*
	 * Estem metodo retorna em bytes a imagem gereda
	 */
	public byte[] getImageEmBytes(String image) {
		String base64Image = image.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

		return imageBytes;
	}

	public String getqRCode() {
		return qRCode;
	}

	public void setqRCode(String qRCode) {
		this.qRCode = qRCode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
