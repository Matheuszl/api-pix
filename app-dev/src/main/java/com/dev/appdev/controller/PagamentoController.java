package com.dev.appdev.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.appdev.pixcontroller.Authentication;
import com.dev.appdev.pixcontroller.Cobranca;
import com.dev.appdev.pixcontroller.QRcode;

@RestController
public class PagamentoController {

//	@PutMapping(value = "deposito")
//	@ResponseBody
	@RequestMapping(value="/{deposito}/{valor}", method=RequestMethod.PUT)
	public ResponseEntity<byte[]> getImage(@PathVariable("valor") String valor) {

		Authentication autentica = new Authentication();
		QRcode qr = new QRcode();
		Cobranca cobranca = new Cobranca();

		String token;
		String resultCob = "";
		String responseQR;
		String image = "";
		byte[] imageName;
		int idCobranca = 0;

		token = autentica.generateToken();
		
		//passa o token e o valor pro saldo
		resultCob = cobranca.realizaCobranca(token, valor);
		
		
		idCobranca = cobranca.getIdCobranca(resultCob);
		responseQR = qr.geradorQrCode(idCobranca, token);
		image = qr.gerarImagemQrCod(responseQR);
		imageName = qr.getImageEmBytes(image);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageName);
	}

}
