package com.dev.appdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.appdev.model.Usuario;
import com.dev.appdev.repository.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired // INGECAO DE DEPENDENCIA DO REPOSITORY
	private UsuarioRepository repo;

	
	/**
	 * retorna uma lista de usuarios
	 * 
	 * ResponseBody: retorna em json o corpo da rsposta, no caso uma lista de usuarios em json
	 */
	@GetMapping(value = "usuarios")
	@ResponseBody
	public ResponseEntity<List<Usuario>> findAll(){
		
		List<Usuario> usuarios =  repo.findAll();
		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		
	}

}
