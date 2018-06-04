package com.zion.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.zion.dao.UsuarioDAO;
import com.zion.dao.PermissoesDAO;
import com.zion.dao.UsuarioPermissaoDAO;
import com.zion.model.Usuario;
import com.zion.model.Permissoes;
import com.zion.model.UsuarioPermissao;
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
@RequestMapping(value = "/usuariopermissao")
public class UsuarioPermissaoController {


    @Autowired
    private UsuarioPermissaoDAO usuarioPermissaoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PermissoesDAO permissoesDAO;

    @RequestMapping
    public String usuariopermissao(){return "usuariospermissao";}

    @RequestMapping(value = "/inserir", method = RequestMethod.POST, produces = "application/json")
    public void inserirNovo(
            @JsonProperty("usuario") String usuario,
            @JsonProperty("permissao") String permissao
    ) {
        try {
            UsuarioPermissao usuariopermissao = new UsuarioPermissao();

            usuariopermissao.setUsuario(usuarioDAO.findOneById(usuario));
            usuariopermissao.setPermissoes(permissoesDAO.findOneById(permissao));

            usuarioPermissaoDAO.save(usuariopermissao);
        } catch (Exception e) {
            throw e;
        }

    }

    @RequestMapping(value = "/renderPerm", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Permissoes>> renderPerm(){
        try{
            List<Permissoes> permissoes = permissoesDAO.findAll();


            return new ResponseEntity<List<Permissoes>>(permissoes, HttpStatus.OK);

        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/renderUsr", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Usuario>> renderUsr(){
        try{
            List<Usuario> usuarios= usuarioDAO.findAll();


            return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

        }catch (Exception e){
            throw e;
        }
    }
}
