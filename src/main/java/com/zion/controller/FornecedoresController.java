package com.zion.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zion.dao.FornecedoresDAO;
import com.zion.model.Fornecedores;
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
@RequestMapping(value = "/fornecedores")
public class FornecedoresController {

    @Autowired
    private FornecedoresDAO fornecedoresdao;

    @RequestMapping
    public String fornecedor(){
        return ("fornecedores");
    }

    @RequestMapping(value = "/inserir", method = RequestMethod.POST)
    public ResponseEntity inserir(
            @JsonProperty(value = "razSocial")String razSocial,
            @JsonProperty(value = "nomeFant")String nomeFant,
            @JsonProperty(value = "cnpj")String cnpj,
            @JsonProperty(value = "endFor")String endFor,
            @JsonProperty(value = "telFor")String telFor,
            @JsonProperty(value = "emailFor")String emailFor
    ){

        try{
            Fornecedores fornecedores = new Fornecedores();

            fornecedores.setRazsocial(razSocial);
            fornecedores.setCnpj(cnpj);
            fornecedores.setEmail(emailFor);
            fornecedores.setEndereco(endFor);
            fornecedores.setNomefant(nomeFant);
            fornecedores.setTelefone(telFor);
            fornecedoresdao.save(fornecedores);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/consulta", method = RequestMethod.GET, produces = "application/json")
    public String fornecedores() { return "consultaFornecedores.html"; }

    @RequestMapping(value = "/consulta/lista", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Fornecedores>> consultaFornecedores(){
        try {
            List<Fornecedores> flist = fornecedoresdao.findAll();

            return new ResponseEntity<List<Fornecedores>>(flist, HttpStatus.OK);

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/editarFornecedor", method = RequestMethod.POST, produces = "application/json")
    public void editarFornecedor(
            @JsonProperty(value = "id")String id,
            @JsonProperty(value = "razSocial")String razSocial,
            @JsonProperty(value = "nomeFant")String nomeFant,
            @JsonProperty(value = "cnpj")String cnpj,
            @JsonProperty(value = "endFor")String endFor,
            @JsonProperty(value = "telFor")String telFor,
            @JsonProperty(value = "emailFor")String emailFor
    ){
        try{
            Fornecedores f = fornecedoresdao.findOneById(id);

            f.setRazsocial(razSocial);
            f.setNomefant(nomeFant);
            f.setCnpj(cnpj);
            f.setEndereco(endFor);
            f.setTelefone(telFor);
            f.setEmail(emailFor);
            fornecedoresdao.save(f);

        }catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST, produces = "application/json")
    public void excluirFornecedor(@JsonProperty(value = "id")String id){
        try{
            fornecedoresdao.delete(fornecedoresdao.findOneById(id));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/getfor", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONObject> consultaPorId(@JsonProperty(value = "id")String id){
        try{
            JSONObject j = JSONObject.fromObject(fornecedoresdao.findOneById(id));

            return new ResponseEntity<JSONObject>(j, HttpStatus.OK);

        }catch (Exception e){
            throw e;
        }
    }



}
