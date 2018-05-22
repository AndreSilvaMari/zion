package com.zion.controller;

import com.zion.dao.PermissoesDAO;
import com.zion.model.Permissoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/permissoes")
public class PermissoesController {

    @Autowired
    private PermissoesDAO permissoesdao;

    @RequestMapping(value = "/inserir/{ativo}/{desc}/{nome}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Permissoes inserirNovo(@PathVariable("ativo") Boolean ativo, @PathVariable("desc") String desc, @PathVariable("nome") String nome) {

        Permissoes permissoes = new Permissoes();

        permissoes.setAtivo(ativo);
        permissoes.setDescricao(desc);
        permissoes.setNome(nome);
        permissoesdao.save(permissoes);
        return permissoes;
    }
}
