package com.zion.controller;


import com.zion.dao.ComprasDAO;
import com.zion.dao.ContasPagarDAO;
import com.zion.dao.FornecedoresDAO;
import com.zion.model.Compras;
import com.zion.model.ContasPagar;
import com.zion.model.Fornecedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/contaspagar")
public class ContasPagarController {

    @Autowired
    private ContasPagarDAO contasPagarDAO;

    @Autowired
    private ComprasDAO comprasDAO;

    @Autowired
    private FornecedoresDAO fornecedoresDAO;

    @RequestMapping(value = "/inserir/{nparcelas}/{vparcelas}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ContasPagar inserirNovo(@PathVariable("nparcelas") Integer nparcelas,@PathVariable("vparcelas") Double vparcelas){

        ContasPagar contaspagar = new ContasPagar();

        contaspagar.setNumeroParcelas(nparcelas);
        contaspagar.setValorParcela(vparcelas);

        Compras compras = comprasDAO.findFirstByNf("teste");
        Fornecedores fornecedores = fornecedoresDAO.findFirstByRazsocial("teste");

        contaspagar.setCompras(compras);
        contaspagar.setFornecedores(fornecedores);

        contasPagarDAO.save(contaspagar);

        return contaspagar;
    }
}
