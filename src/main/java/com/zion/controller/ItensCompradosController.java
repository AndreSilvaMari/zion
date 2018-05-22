package com.zion.controller;


import com.zion.dao.ItensCompradosDAO;
import com.zion.dao.ProdutosDAO;
import com.zion.dao.ComprasDAO;
import com.zion.model.ItensComprados;
import com.zion.model.Produtos;
import com.zion.model.Compras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/itenscomprados")
public class ItensCompradosController {

    @Autowired
    private ItensCompradosDAO itensCompradosDAO;

    @Autowired
    private ProdutosDAO produtosDAO;
    @Autowired
    private ComprasDAO comprasDAO;

    @RequestMapping(value = "/inserir/{qtdvenda}/{preco}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ItensComprados inserirNovo(@PathVariable("qtdvenda") Integer qtdvenda,@PathVariable("preco") Double preco){

        ItensComprados itenscomprados = new ItensComprados();

        itenscomprados.setQuantidadeCompra(qtdvenda);
        itenscomprados.setPrecoCompra(preco);

        Produtos produtos = produtosDAO.findFirstByNome("teste");
        Compras compras = comprasDAO.findFirstByNf("teste");

        itenscomprados.setProdutos(produtos);
        itenscomprados.setCompra(compras);

        itensCompradosDAO.save(itenscomprados);

        return itenscomprados;
    }
}
