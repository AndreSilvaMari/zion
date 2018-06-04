package com.zion.dao;


import com.zion.model.Produtos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProdutosDAO extends CrudRepository<Produtos,String> {
    Produtos findFirstByNome(String nome);

    List<Produtos> findAll();

    Produtos findOneById(String id);

}
