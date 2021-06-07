package com.dev.appdev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * ResponseBody: retorna em json o corpo da rsposta, no caso uma lista de
	 * usuarios em json
	 */
	@GetMapping(value = "usuarios")
	@ResponseBody
	public ResponseEntity<List<Usuario>> findAll() {

		List<Usuario> usuarios = repo.findAll();

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}

	/**
	 * 
	 * @param usuario
	 * @return salva usuario novo e retorna json do novo usuario
	 */
	@PostMapping(value = "usuario")
	@ResponseBody // para desc da resposta
	public ResponseEntity<?> save(@RequestBody Usuario usuario) {
		Usuario user = repo.save(usuario);
		
		if(usuario.getNome() == null) {
			return new ResponseEntity<String>("Nome do usuario invalio!", HttpStatus.OK);
		}

		// usa o response pra retornar o valor e o status
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
	
	/**
	 * O id nunca é passado por http por isso tem que ser passado no corpo da mensagem
	 * 
	 * @param id_usuario
	 * @return usuario deletado e mensagem que deu certo
	 */
	@DeleteMapping(value = "usuario")
	@ResponseBody // para desc da resposta
	public ResponseEntity<String> delete(@RequestParam long id_usuario) {
		repo.deleteById(id_usuario);

		// usa o response pra retornar o valor e o status
		return new ResponseEntity<String>("Usuario deletado com sucesso!", HttpStatus.OK);
	}
	
	/**
	 * O GET INTERCEPTA DADOS DE UM FORMULARIO POR ISSO DEVE SER USADO UM FORMA NA HORA DA REQUISCAO
	 * 
	 * @param id_usuario
	 * @return retorna usurio pesquisado por id
	 */
	@GetMapping(value = "usuario")
	@ResponseBody // para desc da resposta
	public ResponseEntity<Usuario> findById(@RequestParam(name="id_usuario") long id_usuario) {
		Usuario user = repo.findById(id_usuario).get();

		// usa o response pra retornar o valor e o status
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	/**
	 * ?= retorno generico
	 * @param usuario
	 * @return
	 */
	@PutMapping(value = "usuario")
	@ResponseBody // para desc da resposta
	public ResponseEntity<?> update(@RequestBody Usuario usuario) {
		
		if(usuario.getId() == null) {
			return new ResponseEntity<String>("ID nao informado!", HttpStatus.OK);
		}
		if(usuario.getNome() == null) {
			return new ResponseEntity<String>("Nome do usuario invalio!", HttpStatus.OK);
		}
		
		Usuario user = repo.saveAndFlush(usuario);

		// usa o response pra retornar o valor e o status
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "usuarioNome")
	@ResponseBody // para desc da resposta
	public ResponseEntity<List<Usuario>> findByName(@RequestParam(name="nome_usuario") String nome_usuario) {
		
		List<Usuario> users = repo.findByName(nome_usuario);

		// usa o response pra retornar o valor e o status
		return new ResponseEntity<List<Usuario>>(users, HttpStatus.OK);
	}

}
