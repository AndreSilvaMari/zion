package com.zion.dao;

import com.zion.model.Compras;

import org.springframework.data.repository.CrudRepository;

public interface ComprasDAO extends CrudRepository<Compras,String> {
    Compras findFirstByNf(String nf);

}
