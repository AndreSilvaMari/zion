package com.zion.dao;


import com.zion.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDAO extends CrudRepository<Usuario,String> {
    Usuario findFirstByNome(String nome);

}
