package com.zion.controller;
import com.zion.dao.OrdensProducaoDAO;
import com.zion.dao.ItensVendidosDAO;
import com.zion.model.ItensVendidos;
import com.zion.model.OrdensProducao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ordensProducao")
public class OrdensProducaoController {

    @Autowired
    private OrdensProducaoDAO ordensProducaodao;

    @Autowired
    private ItensVendidosDAO itensVendidosDAO;

    @RequestMapping(value = "/inserir/{qtd}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    OrdensProducao inserirNovo(@PathVariable("qtd") Integer qtd){

        OrdensProducao ordensproducao = new OrdensProducao();

        ItensVendidos itensVendidos = itensVendidosDAO.findFirstById("40288fe8621a1da701621a20c98a0000");

        ordensproducao.setQuantidade(qtd);
        ordensproducao.setItensVendidos(itensVendidos);


        ordensProducaodao.save(ordensproducao);
        return ordensproducao;
    }
}
