package com.zion.controller;


import com.zion.dao.ComprasDAO;
import com.zion.dao.FornecedoresDAO;
import com.zion.model.Compras;

import com.zion.model.Fornecedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/compras")
public class ComprasController {

    @Autowired
    private ComprasDAO comprasdao;

    @Autowired
    private FornecedoresDAO fornecedoresDAO;

    @RequestMapping
    public String compra(){
        return ("compras.html");
    }

    @RequestMapping(value = "/inserir/{nf}/{parcelas}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Compras inserirNovo(@PathVariable("nf") String nf,@PathVariable("parcelas") Integer parcelas){

        Compras compras = new Compras();

        compras.setNf(nf);
        compras.setParcelas(parcelas);
        Fornecedores fornecedores = fornecedoresDAO.findFirstByRazsocial("xablau");
        compras.setFornecedores(fornecedores);
        comprasdao.save(compras);
        return compras;
    }
}
