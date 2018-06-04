package com.zion.dao;

import com.zion.model.Fornecedores;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FornecedoresDAO extends CrudRepository<Fornecedores,String> {
    Fornecedores findFirstByRazsocial(String razsocial);
    Fornecedores findFirstById(String id);

    List<Fornecedores> findAll();

    Fornecedores findOneById(String id);

    Fornecedores findFirstByCnpj(String cnpj);

}
