package com.zion.controller;


import com.zion.dao.ClienteJurDAO;
import com.zion.dao.VendasDAO;
import com.zion.dao.ClienteFisDAO;
import com.zion.model.ClienteJur;
import com.zion.model.Vendas;
import com.zion.model.ClienteFis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/vendas")
public class VendasController {

    @Autowired
    private VendasDAO vendasdao;

    @Autowired
    private ClienteFisDAO clientefisDAO;

    @Autowired
    private ClienteJurDAO clientejurDAO;

    @RequestMapping
    public String venda(){
        return ("vendas.html");
    }

    @RequestMapping(value = "/inserir/{nf}/{nparcelas}/{vparcelas}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Vendas inserirNovo(@PathVariable("nf") String nf,@PathVariable("nparcelas") Integer nparcelas,@PathVariable("vparcelas") Double vparcelas){

        Vendas vendas = new Vendas();

        vendas.setNf(nf);
        vendas.setNumeroParcelas(nparcelas);
        vendas.setValorParcela(vparcelas);

        ClienteFis clientefis = clientefisDAO.findFirstByCpf("teste");
        ClienteJur clientejur = clientejurDAO.findFirstByCnpj("teste");

        vendas.setClienteFis(clientefis);
        vendas.setClienteJur(clientejur);
        vendasdao.save(vendas);
        return vendas;
    }
}
