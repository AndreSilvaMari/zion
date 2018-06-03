package com.zion.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zion.dao.ClienteFisDAO;
import com.zion.model.ClienteFis;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/clientefis")
public class ClientesFisController {

    @Autowired
    private ClienteFisDAO clientefisdao;

    @RequestMapping
    public String cliente(){
        return ("clientes");
    }

    @RequestMapping(value = "/consulta")
    public String consultaClienteFis(){
        return ("consultaClientesFis.html");
    }

    @RequestMapping(value = "/inserir",method = RequestMethod.POST)
    public ResponseEntity inserir(
            @JsonProperty(value = "nomeFis")String nomeFis,
            @JsonProperty(value = "cpfFis")String cpfFis,
            @JsonProperty(value = "endFis")String endFis,
            @JsonProperty(value = "telFis")String telFis,
            @JsonProperty(value = "emailFis")String emailFis
    ){
        try {
            ClienteFis c = new ClienteFis();
            c.setNome(nomeFis);
            c.setCpf(cpfFis);
            c.setEndereco(endFis);
            c.setTelefone(telFis);
            c.setEmail(emailFis);
            clientefisdao.save(c);
            return new ResponseEntity(HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/consulta/fis", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List<ClienteFis>> consultarClientes(){
                try{
                    List<ClienteFis> clist = clientefisdao.findAll();

                    return new ResponseEntity<List<ClienteFis>>(clist,HttpStatus.OK);
                }catch(Exception e){
                    throw e;
                }
    }

    @RequestMapping(value = "/editarCliente", method = RequestMethod.POST, produces = "application/json")
    public void editarCliente(
            @JsonProperty(value = "id")String id,
            @JsonProperty(value = "nomeFis")String nomeFis,
            @JsonProperty(value = "cpfFis")String cpfFis,
            @JsonProperty(value = "endFis")String endFis,
            @JsonProperty(value = "telFis")String telFis,
            @JsonProperty(value = "emailFis")String emailFis

    ){
        try{
            ClienteFis cl = clientefisdao.findOneById(id);

            cl.setNome(nomeFis);
            cl.setCpf(cpfFis);
            cl.setEndereco(endFis);
            cl.setTelefone(telFis);
            cl.setEmail(emailFis);

            clientefisdao.save(cl);

        }catch(Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST, produces = "application/json")
    public void excluirCliente(@JsonProperty(value = "id")String id){
                try{
                    clientefisdao.delete(clientefisdao.findOneById(id));

                }catch (Exception e){
                    e.printStackTrace();
                }

    }

    @RequestMapping(value = "/getfis", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<JSONObject> consultaPorId(@JsonProperty(value = "id")String id){
        try{
            JSONObject j = JSONObject.fromObject(clientefisdao.findOneById(id));

            return new ResponseEntity<JSONObject>(j, HttpStatus.OK);

        }catch (Exception e){
           throw e;
        }

    }


}
