package com.zion.controller;


import com.zion.dao.ProdutosDAO;
import com.zion.dao.ItensVendidosDAO;
import com.zion.dao.VendasDAO;
import com.zion.model.Produtos;
import com.zion.model.ItensVendidos;
import com.zion.model.Vendas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/itensvendidos")
public class ItensVendidosController {

    @Autowired
    private ItensVendidosDAO itensVendidosDAO;

    @Autowired
    private ProdutosDAO produtosDAO;
    @Autowired
    private VendasDAO vendasDAO;

    @RequestMapping(value = "/inserir/{qtdvenda}/{preco}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ItensVendidos inserirNovo(@PathVariable("qtdvenda") Integer qtdvenda,@PathVariable("preco") Double preco){

        ItensVendidos itensvendidos = new ItensVendidos();

        itensvendidos.setQuantidadeVenda(qtdvenda);
        itensvendidos.setPrecoVenda(preco);

        Produtos produtos = produtosDAO.findFirstByNome("teste");
        Vendas vendas = vendasDAO.findFirstByNf("teste");

        itensvendidos.setProdutos(produtos);
        itensvendidos.setVendas(vendas);

        itensVendidosDAO.save(itensvendidos);

        return itensvendidos;
    }
}
