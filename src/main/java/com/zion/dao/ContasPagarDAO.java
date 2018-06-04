package com.zion.dao;

import com.zion.model.Compras;
import com.zion.model.ContasPagar;

import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ContasPagarDAO extends CrudRepository<ContasPagar,String> {
    ContasPagar findFirstById(String id);
    List<ContasPagar> findAllByCompras(Compras compras);
}
