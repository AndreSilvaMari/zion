package com.zion.dao;

import com.zion.model.ContasPagar;

import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ContasPagarDAO extends CrudRepository<ContasPagar,String> {
    ContasPagar findFirstById(String id);
}
