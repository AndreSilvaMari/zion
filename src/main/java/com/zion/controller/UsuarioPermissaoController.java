package com.zion.controller;


import com.zion.dao.UsuarioDAO;
import com.zion.dao.PermissoesDAO;
import com.zion.dao.UsuarioPermissaoDAO;
import com.zion.model.Usuario;
import com.zion.model.Permissoes;
import com.zion.model.UsuarioPermissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/usuariopermissao")
public class UsuarioPermissaoController {


    @Autowired
    private UsuarioPermissaoDAO usuarioPermissaoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PermissoesDAO permissoesDAO;

    @RequestMapping(value = "/inserir/{usuario}/{permissao}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    UsuarioPermissao inserirNovo(@PathVariable("usuario") String usuario,@PathVariable("permissao") String permissao){

        UsuarioPermissao usuariopermissao = new UsuarioPermissao();


        Usuario u = usuarioDAO.findFirstByNome(usuario);
        Permissoes permissoes = permissoesDAO.findFirstByNome(permissao);

        usuariopermissao.setUsuario(u);
        usuariopermissao.setPermissoes(permissoes);

        usuarioPermissaoDAO.save(usuariopermissao);
        return usuariopermissao;
    }
}
