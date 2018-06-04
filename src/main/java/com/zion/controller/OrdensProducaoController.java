package com.zion.controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zion.dao.OrdensProducaoDAO;
import com.zion.dao.ItensVendidosDAO;
import com.zion.dao.ProdutosDAO;
import com.zion.model.ItensVendidos;
import com.zion.model.OrdensProducao;
import com.zion.model.Produtos;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/ops")
public class OrdensProducaoController {

    @Autowired
    private OrdensProducaoDAO ordensProducaodao;

    @Autowired
    private ProdutosDAO produtosDAO;

    @RequestMapping
    public String ops(){return "ops";}

    @RequestMapping(value = "/inserir", method = RequestMethod.POST, produces = "application/json")
    public void inserirNovo(
            @JsonProperty(value = "produto")String produto,
            @JsonProperty(value = "qtd")String qtd
    ){
        try {
            OrdensProducao ordensproducao = new OrdensProducao();

            ordensproducao.setQuantidade(Integer.parseInt(qtd));
            Date data = new Date();
            ordensproducao.setDtcriacao(data);
            ordensproducao.setDtalteracao(data);
            ordensproducao.setProduto(produtosDAO.findOneById(produto));

            ordensProducaodao.save(ordensproducao);
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/renderProds", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Produtos>> renderProds(){
                try{
                    List<Produtos> p = produtosDAO.findAll();

                    return new ResponseEntity<List<Produtos>>(p, HttpStatus.OK);

                }catch (Exception e){
                    throw e;
                }
    }

    @RequestMapping(value = "/consulta")
    public String opsConsulta(){ return "consultaOps";}

    @RequestMapping(value = "/consulta/lista", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONArray> consultaOrdensProducao() {
        try {

            JSONArray a = new JSONArray();
            JSONObject j = new JSONObject();

            List<OrdensProducao> lPerm = ordensProducaodao.findAll();

            for (int i = 0; i < lPerm.size() ; i++){
                j.put("produto", lPerm.get(i).getProduto().getNome());
                j.put("dataCriacao", lPerm.get(i).getDtcriacao());
                j.put("quantidade", lPerm.get(i).getQuantidade());
                j.put("id", lPerm.get(i).getId());
                a.add(j);
            }



            return new ResponseEntity<JSONArray>(a, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/editarOrdensProducao", method = RequestMethod.POST, produces = "application/json")
    public void editarOrdensProducao(
            @JsonProperty(value = "id")String id,
            @JsonProperty(value = "produto")String produto,
            @JsonProperty(value = "quantidade")String quantidade
    )
    {
        try{
            OrdensProducao p = ordensProducaodao.findOneById(id);


            p.setProduto(produtosDAO.findOneById(produto));
            p.setQuantidade(Integer.parseInt(quantidade));

            ordensProducaodao.save(p);

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST, produces = "application/json")
    public void excluirOrdensProducao(@JsonProperty(value = "id")String id){
        try{
            ordensProducaodao.delete(ordensProducaodao.findOneById(id));

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONObject> consultaPorId(
            @JsonProperty(value = "id")String id
    ){
        try{
            JSONObject j = JSONObject.fromObject(ordensProducaodao.findOneById(id));

            return new ResponseEntity<JSONObject>(j,HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }
}
