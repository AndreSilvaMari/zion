package com.zion.controller;


import com.zion.dao.ClienteFisDAO;
import com.zion.dao.VendasDAO;
import com.zion.dao.ContasReceberDAO;
import com.zion.model.ClienteFis;
import com.zion.model.Vendas;
import com.zion.model.ContasReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/contasreceber")
public class ContasReceberController {

    @Autowired
    private ContasReceberDAO contasReceberDAO;

    @Autowired
    private ClienteFisDAO clientefisDAO;

    @Autowired
    private VendasDAO vendasDAO;

    @RequestMapping(value = "/inserir/{nparcelas}/{vparcelas}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ContasReceber inserirNovo(@PathVariable("nparcelas") Integer nparcelas,@PathVariable("vparcelas") Double vparcelas){

        ContasReceber contasreceber = new ContasReceber();

        contasreceber.setNumeroParcelas(nparcelas);
        contasreceber.setValorParcela(vparcelas);

        ClienteFis clientefis = clientefisDAO.findFirstByCpf("teste");
        Vendas vendas = vendasDAO.findFirstByNf("teste");

        contasreceber.setClienteFis(clientefis);
        contasreceber.setVendas(vendas);

        contasReceberDAO.save(contasreceber);

        return contasreceber;
    }
}
