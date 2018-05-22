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
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuariodao;

    @Autowired
    private UsuarioPermissaoDAO usuariopermissoesDAO;

    @RequestMapping(value = "/inserir/{nome}/{login}/{email}/{senha}/{ativo}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Usuario inserirNovo(@PathVariable("nome") String nome,@PathVariable("login") String login,@PathVariable("email") String email,@PathVariable("senha") String senha,@PathVariable("ativo") Boolean ativo){

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setAtivo(ativo);
        usuariodao.save(usuario);
        return usuario;
    }
}
