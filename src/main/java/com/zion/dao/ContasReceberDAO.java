package com.zion.dao;

import com.zion.model.ContasReceber;

import com.zion.model.Vendas;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ContasReceberDAO extends CrudRepository<ContasReceber,String> {
    ContasReceber findFirstById(String id);

    List<ContasReceber> findAllByVendas(Vendas vendas);

}
