package com.zion.dao;


import com.zion.model.OrdensProducao;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface OrdensProducaoDAO extends CrudRepository<OrdensProducao,String> {
    OrdensProducao findFirstById(String id);

}
