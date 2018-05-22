package com.zion.dao;


import com.zion.model.ItensComprados;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ItensCompradosDAO extends CrudRepository<ItensComprados,String> {
    ItensComprados findFirstById(String id);

}
