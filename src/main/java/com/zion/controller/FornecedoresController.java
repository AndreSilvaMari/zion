package com.zion.controller;

import com.zion.dao.FornecedoresDAO;
import com.zion.model.Fornecedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/fornecedores")
public class FornecedoresController {

    @Autowired
    private FornecedoresDAO fornecedoresdao;

    @RequestMapping
    public String fornecedor(){
        return ("fornecedores.html");
    }

    @RequestMapping(value = "/inserir/{raz}/{cnpj}/{email}/{end}/{nomef}/{tel}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Fornecedores inserirNovo(@PathVariable("raz") String raz, @PathVariable("cnpj") String cnpj, @PathVariable("email") String email,
    @PathVariable("end") String end, @PathVariable("nomef") String nomef,@PathVariable("tel")String tel) {

        Fornecedores fornecedores = new Fornecedores();

        fornecedores.setRazsocial(raz);
        fornecedores.setCnpj(cnpj);
        fornecedores.setEmail(email);
        fornecedores.setEndereco(end);
        fornecedores.setNomefant(nomef);
        fornecedores.setTelefone(tel);
        fornecedoresdao.save(fornecedores);
        return fornecedores;
    }
}
