package com.zion.dao;


import com.zion.model.Compras;
import com.zion.model.ItensComprados;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ItensCompradosDAO extends CrudRepository<ItensComprados,String> {
    ItensComprados findFirstById(String id);

    List<ItensComprados> findAllByCompras(Compras compras);

}
