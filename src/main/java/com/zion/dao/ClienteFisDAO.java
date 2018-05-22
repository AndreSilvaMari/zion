package com.zion.dao;

import com.zion.model.ClienteFis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteFisDAO extends CrudRepository<ClienteFis,String> {
    ClienteFis findFirstByCpf(String cpf);

    List<ClienteFis> findAll();
    ClienteFis findOneById(String id);

}
