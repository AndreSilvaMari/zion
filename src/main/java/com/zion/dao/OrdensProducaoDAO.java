package com.zion.dao;


import com.zion.model.OrdensProducao;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface OrdensProducaoDAO extends CrudRepository<OrdensProducao,String> {
    OrdensProducao findFirstById(String id);

    List<OrdensProducao> findAll();
    OrdensProducao findOneById(String id);

}
