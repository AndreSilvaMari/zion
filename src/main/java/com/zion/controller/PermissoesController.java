package com.zion.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zion.dao.PermissoesDAO;
import com.zion.model.Permissoes;
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
@RequestMapping(value = "/permissoes")
public class PermissoesController {

    @Autowired
    private PermissoesDAO permissoesdao;

    @RequestMapping
    public String permissoes(){
        return ("permissoes");
    }

    @RequestMapping(value = "/inserir", method = RequestMethod.POST, produces = "application/json")
    public void inserirNovo(
            @JsonProperty(value = "ativo") Boolean ativo,
            @JsonProperty(value = "desc") String desc,
            @JsonProperty(value = "nome") String nome
    ) {
        try {
            Permissoes permissoes = new Permissoes();

            permissoes.setAtivo(ativo);
            permissoes.setDescricao(desc);
            permissoes.setNome(nome);
            permissoesdao.save(permissoes);

        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/consulta")
    public String permissoesConsulta(){return "consultaPermissoes";}

    @RequestMapping(value = "/consulta/lista", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Permissoes>> consultaPermissoes() {
        try {
            List<Permissoes> lPerm = permissoesdao.findAll();

            return new ResponseEntity<List<Permissoes>>(lPerm, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/editarPermissoes", method = RequestMethod.POST, produces = "application/json")
    public void editarPermissoes(
            @JsonProperty(value = "id")String id,
            @JsonProperty(value = "nome")String nome,
            @JsonProperty(value = "login")String desc,
            @JsonProperty(value = "ativo")Boolean ativo
    )
    {
        try{
            Permissoes p = permissoesdao.findOneById(id);

            p.setNome(nome);
            p.setDescricao(desc);

            p.setAtivo(ativo);

            permissoesdao.save(p);

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST, produces = "application/json")
    public void excluirPermissoes(@JsonProperty(value = "id")String id){
        try{
            permissoesdao.delete(permissoesdao.findOneById(id));

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/getPerm", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONObject> consultaPorId(@JsonProperty(value = "id")String id){
        try{
            JSONObject j = JSONObject.fromObject(permissoesdao.findOneById(id));

            return new ResponseEntity<JSONObject>(j,HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

}
