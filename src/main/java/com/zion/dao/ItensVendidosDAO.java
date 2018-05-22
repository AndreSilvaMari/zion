package com.zion.dao;


import com.zion.model.ItensVendidos;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ItensVendidosDAO extends CrudRepository<ItensVendidos,String> {
    ItensVendidos findFirstById(String id);

}
