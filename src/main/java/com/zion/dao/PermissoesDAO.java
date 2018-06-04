package com.zion.dao;


import com.zion.model.Permissoes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermissoesDAO extends CrudRepository<Permissoes,String> {
    Permissoes findFirstByNome(String nome);

    List<Permissoes> findAll();
    Permissoes findOneById(String id);

}
