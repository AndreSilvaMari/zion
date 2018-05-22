package com.zion.dao;


import com.zion.model.Vendas;
import org.springframework.data.repository.CrudRepository;

public interface VendasDAO extends CrudRepository<Vendas,String> {
    Vendas findFirstByNf(String nf);

}
