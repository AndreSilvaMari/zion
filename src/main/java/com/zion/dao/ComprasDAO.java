package com.zion.dao;

import com.zion.model.Compras;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComprasDAO extends CrudRepository<Compras,String> {
    Compras findFirstByNf(String nf);

    List<Compras> findAll();
    Compras findOneById(String id);

}
