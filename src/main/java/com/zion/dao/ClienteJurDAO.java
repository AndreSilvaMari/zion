package com.zion.dao;

import com.zion.model.ClienteJur;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteJurDAO extends CrudRepository<ClienteJur,String> {
    ClienteJur findFirstByCnpj(String cnpj);

    List<ClienteJur> findAll();

    ClienteJur findOneById(String id);

}
