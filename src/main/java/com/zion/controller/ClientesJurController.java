package com.zion.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zion.dao.ClienteJurDAO;
import com.zion.model.ClienteJur;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/clientejur")
public class ClientesJurController {

    @Autowired
    private ClienteJurDAO clienteJurDAO;

    @RequestMapping
    public String cliente(){
        return ("clientes.html");
    }

    @RequestMapping(value = "/inserir",method = RequestMethod.POST)
    public ResponseEntity inserir(
            @JsonProperty(value = "razSocial")String razSocial,
            @JsonProperty(value = "nomeFant")String nomeFant,
            @JsonProperty(value = "cnpj")String cnpj,
            @JsonProperty(value = "im")String im,
            @JsonProperty(value = "endJur")String endJur,
            @JsonProperty(value = "telJur")String telJur,
            @JsonProperty(value = "emailJur")String emailJur
    ){
        try {
            ClienteJur c = new ClienteJur();
            c.setRazao(razSocial);
            c.setNomeFant(nomeFant);
            c.setCnpj(cnpj);
            c.setIm(im);
            c.setEndereco(endJur);
            c.setTelefone(telJur);
            c.setEmail(emailJur);
            clienteJurDAO.save(c);
            return new ResponseEntity(HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/consulta")
    public String consultaClienteFis(){
        return ("consultaClientesJur.html");
    }

    @RequestMapping(value = "/consulta/jur", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List<ClienteJur>> consultarClientes(){
        try{
            List<ClienteJur> clist = clienteJurDAO.findAll();

            return new ResponseEntity<List<ClienteJur>>(clist,HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST, produces = "application/json")
    public void excluirCliente(@JsonProperty(value = "id")String id){
        try{
            clienteJurDAO.delete(clienteJurDAO.findOneById(id));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/getjur", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<JSONObject> consultaPorId(@JsonProperty(value = "id")String id){
        try{
            JSONObject j = JSONObject.fromObject(clienteJurDAO.findOneById(id));

            return new ResponseEntity<JSONObject>(j, HttpStatus.OK);

        }catch (Exception e){
            throw e;
        }

    }

    @RequestMapping(value = "/editarCliente", method = RequestMethod.POST, produces = "application/json")
    public void editarCliente(
            @JsonProperty(value = "id")String id,
            @JsonProperty(value = "razSocial")String razSocial,
            @JsonProperty(value = "nomeFant")String nomeFant,
            @JsonProperty(value = "cnpj")String cnpj,
            @JsonProperty(value = "im")String im,
            @JsonProperty(value = "endJur")String endJur,
            @JsonProperty(value = "telJur")String telJur,
            @JsonProperty(value = "emailJur")String emailJur

    ){
        try{
            ClienteJur cj = clienteJurDAO.findOneById(id);

            cj.setRazao(razSocial);
            cj.setNomeFant(nomeFant);
            cj.setCnpj(cnpj);
            cj.setIm(im);
            cj.setEndereco(endJur);
            cj.setTelefone(telJur);
            cj.setEmail(emailJur);
            clienteJurDAO.save(cj);

        }catch(Exception e){
            throw e;
        }
    }



}
