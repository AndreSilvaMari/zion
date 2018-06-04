package com.zion.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.regexp.internal.RE;
import com.zion.dao.UsuarioDAO;
import com.zion.dao.PermissoesDAO;
import com.zion.dao.UsuarioPermissaoDAO;
import com.zion.model.Usuario;
import com.zion.model.Permissoes;
import com.zion.model.UsuarioPermissao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;
import java.util.List;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuariodao;

    @Autowired
    private UsuarioPermissaoDAO usuariopermissoesDAO;

    @RequestMapping
    public String usuario(){
        return ("usuarios");
    }

    @RequestMapping(value = "/inserir", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity inserirNovo(
            @JsonProperty(value = "nome") String nome,
            @JsonProperty(value = "login") String login,
            @JsonProperty(value = "email") String email,
            @JsonProperty(value = "senha") String senha,
            @JsonProperty(value = "ativo") Boolean ativo
    ) {
        try {

            Usuario usuario = new Usuario();

            usuario.setNome(nome);
            usuario.setLogin(login);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setAtivo(ativo);
            usuariodao.save(usuario);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/consulta")
    public String usuarios(){return "consultaUsuarios";}

    @RequestMapping(value = "/consulta/lista", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Usuario>> consultaUsuarios() {
        try {
            List<Usuario> lUsrs = usuariodao.findAll();

            return new ResponseEntity<List<Usuario>>(lUsrs, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/editarUsuario", method = RequestMethod.POST, produces = "application/json")
    public void editarUsuario(
            @JsonProperty(value = "id")String id,
            @JsonProperty(value = "nome")String nome,
            @JsonProperty(value = "login")String login,
            @JsonProperty(value = "email")String email,
            @JsonProperty(value = "senha")String senha,
            @JsonProperty(value = "ativo")Boolean ativo
    )
    {
        try{
            Usuario u = usuariodao.findOneById(id);

            u.setNome(nome);
            u.setLogin(login);
            u.setEmail(email);
            u.setSenha(senha);
            u.setAtivo(ativo);

            usuariodao.save(u);

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST, produces = "application/json")
    public void excluirUsuario(@JsonProperty(value = "id")String id){
        try{
            usuariodao.delete(usuariodao.findOneById(id));

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/getUsr", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONObject> consultaPorId(@JsonProperty(value = "id")String id){
        try{
            JSONObject j = JSONObject.fromObject(usuariodao.findOneById(id));

            return new ResponseEntity<JSONObject>(j,HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONObject> login(
            @JsonProperty(value = "login") String login,
            @JsonProperty(value = "senha") String senha
    ){
        Usuario u = usuariodao.findByLoginAndSenha(login,senha);

        if(u == null){
            JSONObject j = new JSONObject();
            j.put("retorno","Erro");
            return new ResponseEntity<JSONObject>(j,HttpStatus.OK);
        }else{
            JSONObject j = new JSONObject();
            j.put("retorno","ok");
            return new ResponseEntity<JSONObject>(j,HttpStatus.OK);
        }
    }

}

