package com.zion.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zion.dao.ProdutosDAO;
import com.zion.model.Produtos;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosDAO produtosdao;

    @RequestMapping
    public String produto(){
        return ("produtos");
    }

    @RequestMapping(value = "/consulta")
    public String produtoLista(){
        return ("consultaProdutos");
    }

    @RequestMapping(value = "/inserir", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity inserir(
            @JsonProperty(value = "cat") String cat,
            @JsonProperty(value = "desc") String desc,
            @JsonProperty(value = "nome") String nome,
            @JsonProperty(value = "valor") String valor,
            @JsonProperty(value = "qtd") String qtd
    ) {
        try {
            Produtos produtos = new Produtos();

            produtos.setCategoria(cat);
            produtos.setDescricao(desc);
            produtos.setNome(nome);
            produtos.setPreco(Double.parseDouble(valor));
            produtos.setQuantidade(Integer.parseInt(qtd));
            produtosdao.save(produtos);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/consulta/lista", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Produtos>> consultarProdutos(){
        try{
            List<Produtos> clist = produtosdao.findAll();

            return new ResponseEntity<List<Produtos>>(clist,HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/editarProduto", method = RequestMethod.POST, produces = "application/json")
    public void editarProduto(
            @JsonProperty(value = "id") String id,
            @JsonProperty(value = "cat") String cat,
            @JsonProperty(value = "desc") String desc,
            @JsonProperty(value = "nome") String nome,
            @JsonProperty(value = "valor") String valor,
            @JsonProperty(value = "qtd") String qtd
    ){
        try{
            Produtos p = produtosdao.findOneById(id);

            p.setCategoria(cat);
            p.setDescricao(desc);
            p.setNome(nome);
            p.setPreco(Double.parseDouble(valor));
            p.setQuantidade(Integer.parseInt(qtd));

            produtosdao.save(p);

        }catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST, produces = "application/json")
    public void excluirProduto(@JsonProperty(value = "id")String id){
        try{
            produtosdao.delete(produtosdao.findOneById(id));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/getprod", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONObject> consultaPorId(@JsonProperty(value = "id")String id){
        try{
            JSONObject j = JSONObject.fromObject(produtosdao.findOneById(id));

            return new ResponseEntity<JSONObject>(j, HttpStatus.OK);

        }catch (Exception e){
            throw e;
        }

    }

}
