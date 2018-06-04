package com.zion.dao;


import com.zion.model.Vendas;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VendasDAO extends CrudRepository<Vendas,String> {
    Vendas findFirstByNf(String nf);

    List<Vendas> findAll();

    Vendas findOneById(String id);

}
