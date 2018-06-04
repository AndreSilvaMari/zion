package com.zion.dao;


import com.zion.model.ItensVendidos;
import com.zion.model.Vendas;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ItensVendidosDAO extends CrudRepository<ItensVendidos,String> {
    ItensVendidos findFirstById(String id);

    List<ItensVendidos> findAllByVendas(Vendas v);

}
