package com.zion.dao;


import com.zion.model.Produtos;
import org.springframework.data.repository.CrudRepository;

public interface ProdutosDAO extends CrudRepository<Produtos,String> {
    Produtos findFirstByNome(String nome);

}
