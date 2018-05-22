package com.zion.dao;


import com.zion.model.Permissoes;
import org.springframework.data.repository.CrudRepository;

public interface PermissoesDAO extends CrudRepository<Permissoes,String> {
    Permissoes findFirstByNome(String nome);

}
