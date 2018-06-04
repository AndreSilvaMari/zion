package com.zion.dao;


import com.zion.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioDAO extends CrudRepository<Usuario,String> {
    Usuario findFirstByNome(String nome);

    List<Usuario> findAll();
    Usuario findOneById(String id);

    Usuario findByLoginAndSenha(String login, String senha);

}
