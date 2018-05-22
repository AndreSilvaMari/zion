package com.zion.controller;

import com.zion.dao.ProdutosDAO;
import com.zion.model.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosDAO produtosdao;

    @RequestMapping
    public String produto(){
        return ("produtos.html");
    }

    @RequestMapping(value = "/inserir/{cat}/{desc}/{nome}/{preco}/{qtd}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Produtos inserirNovo(@PathVariable("cat") String cat, @PathVariable("desc") String desc, @PathVariable("nome") String nome,
    @PathVariable("preco") Double preco, @PathVariable("qtd") Integer qtd) {

        Produtos produtos = new Produtos();

        produtos.setCategoria(cat);
        produtos.setDescricao(desc);
        produtos.setNome(nome);
        produtos.setPreço(preco);
        produtos.setQuantidade(qtd);
        produtosdao.save(produtos);
        return produtos;
    }
}
