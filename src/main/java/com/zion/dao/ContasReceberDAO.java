package com.zion.dao;

import com.zion.model.ContasReceber;

import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ContasReceberDAO extends CrudRepository<ContasReceber,String> {
    ContasReceber findFirstById(String id);

}
