package com.dev.appdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.appdev.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/**
	 * JPQL = select u(usuario, é um nome) from Usuario where(onde) u.nome like(pesquisar por partes)
	 * ?1 = apenas um parametro
	 * o spring injeta o nome_usuario e roda no banco de dados
	 * @param nome_usuario
	 * @return um nome ou mais de um
	 */
	@Query(value="select u from Usuario u where u.nome like %?1%")
	List<Usuario> findByName(String nome_usuario);
}
