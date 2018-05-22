package com.zion.dao;


import com.zion.model.Permissoes;
import com.zion.model.Usuario;
import com.zion.model.UsuarioPermissao;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioPermissaoDAO extends CrudRepository<UsuarioPermissao,String> {
    UsuarioPermissao findByUsuarioAndPermissoes(Usuario usuario, Permissoes permissoes);

}
