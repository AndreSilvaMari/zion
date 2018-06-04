package com.zion.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.regexp.internal.RE;
import com.zion.dao.*;
import com.zion.model.*;
import javassist.bytecode.stackmap.BasicBlock;
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

import java.lang.reflect.Array;
import java.util.*;

@Controller
@RequestMapping(value = "/vendas")
public class VendasController {

    @Autowired
    private VendasDAO vendasdao;

    @Autowired
    private ClienteFisDAO clientefisDAO;

    @Autowired
    private ClienteJurDAO clientejurDAO;

    @Autowired
    private ProdutosDAO produtosDAO;

    @Autowired
    private ItensVendidosDAO itensVendidosDAO;

    @Autowired
    private ContasReceberDAO contasReceberDAO;

    @RequestMapping
    public String venda() {
        return ("vendas");
    }

    @RequestMapping(value = "/consultarCNPJ")
    public ResponseEntity<JSONObject> consultarCNPJ(
            @JsonProperty(value = "CNPJ") String CNPJ
    ) {
        try {
            ClienteJur cj = clientejurDAO.findFirstByCnpj(CNPJ);

            JSONObject j = JSONObject.fromObject(cj);

            return new ResponseEntity<JSONObject>(j, HttpStatus.OK);

        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/consultarCPF")
    public ResponseEntity<JSONObject> consultarCPF(
            @JsonProperty(value = "CPF") String CPF
    ) {
        try {
            ClienteFis cf = clientefisDAO.findFirstByCpf(CPF);

            JSONObject j = JSONObject.fromObject(cf);

            return new ResponseEntity<JSONObject>(j, HttpStatus.OK);

        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/finalizarCPF", method = RequestMethod.POST)
    public ResponseEntity finalizarCPF(
            @JsonProperty(value = "codigo") String codigo,
            @JsonProperty(value = "prods") String prods,
            @JsonProperty(value = "parcelas") String parcelas,
            @JsonProperty(value = "nf") String nf
    ) {
        try {

            prods = prods.replace("[", "");
            prods = prods.replace("]", "");
            prods = prods.replace("\"", "");
            prods = prods.replace("\\", "");

            String[] produtos = prods.split(",");


            Vendas v = new Vendas();


            v.setClienteFis(clientefisDAO.findFirstByCpf(codigo));
            Date data = new Date();
            v.setDtcriacao(data);
            v.setDtalteracao(data);
            v.setDtVenda(data);
            v.setNf(nf);
            v.setNumeroParcelas(Integer.parseInt(parcelas));

            vendasdao.save(v);

            int vTotal = 0;

            for (int i = 0; i < produtos.length; i++) {
                ItensVendidos itensVendidos = new ItensVendidos();

                String codProd = produtos[i];
                String qtdProd = codProd.substring(40, codProd.length());
                qtdProd = qtdProd.replace("<", "");
                qtdProd = qtdProd.replace(">", "");
                qtdProd = qtdProd.replace("p", "");
                qtdProd = qtdProd.replace("/", "");
                codProd = codProd.substring(7, 39);

                Produtos p = produtosDAO.findOneById(codProd);

                itensVendidos.setPrecoVenda(p.getPreco());
                itensVendidos.setQuantidadeVenda(Integer.parseInt(qtdProd));
                itensVendidos.setVendas(v);
                itensVendidos.setProdutos(p);
                itensVendidos.setDtcriacao(data);
                itensVendidos.setDtalteracao(data);
                itensVendidosDAO.save(itensVendidos);

                p.setQuantidade(p.getQuantidade() - itensVendidos.getQuantidadeVenda());
                produtosDAO.save(p);

                vTotal += itensVendidos.getQuantidadeVenda() * itensVendidos.getPrecoVenda();
            }

            double totalfinal = vTotal;
            v.setValorParcela(totalfinal);
            vendasdao.save(v);

            for (int i = 0; i < Integer.parseInt(parcelas); i++) {
                ContasReceber contasReceber = new ContasReceber();

                contasReceber.setClienteFis(v.getClienteFis());
                contasReceber.setVendas(v);
                contasReceber.setNumeroParcelas(i + 1);
                double total = ((vTotal / Integer.parseInt(parcelas)));
                contasReceber.setValorParcela(total);

                Calendar c = new GregorianCalendar();
                c.setTime(data);
                c.add(Calendar.DATE, (30 * i));
                Date dataParcela = c.getTime();
                contasReceber.setDtParcela(dataParcela);

                contasReceber.setDtcriacao(data);
                contasReceber.setDtalteracao(data);

                contasReceberDAO.save(contasReceber);
            }


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/finalizarCNPJ", method = RequestMethod.POST)
    public ResponseEntity finalizarCNPJ(
            @JsonProperty(value = "codigo") String codigo,
            @JsonProperty(value = "prods") String prods,
            @JsonProperty(value = "parcelas") String parcelas,
            @JsonProperty(value = "nf") String nf
    ) {
        try {

            prods = prods.replace("[", "");
            prods = prods.replace("]", "");
            prods = prods.replace("\"", "");
            prods = prods.replace("\\", "");

            String[] produtos = prods.split(",");


            Vendas v = new Vendas();


            v.setClienteJur(clientejurDAO.findFirstByCnpj(codigo));
            Date data = new Date();
            v.setDtcriacao(data);
            v.setDtalteracao(data);
            v.setDtVenda(data);
            v.setNf(nf);
            v.setNumeroParcelas(Integer.parseInt(parcelas));

            vendasdao.save(v);

            int vTotal = 0;

            for (int i = 0; i < produtos.length; i++) {
                ItensVendidos itensVendidos = new ItensVendidos();

                String codProd = produtos[i];
                String qtdProd = codProd.substring(40, codProd.length());
                qtdProd = qtdProd.replace("<", "");
                qtdProd = qtdProd.replace(">", "");
                qtdProd = qtdProd.replace("p", "");
                qtdProd = qtdProd.replace("/", "");
                codProd = codProd.substring(7, 39);

                Produtos p = produtosDAO.findOneById(codProd);

                itensVendidos.setPrecoVenda(p.getPreco());
                itensVendidos.setQuantidadeVenda(Integer.parseInt(qtdProd));
                itensVendidos.setVendas(v);
                itensVendidos.setProdutos(p);
                itensVendidos.setDtcriacao(data);
                itensVendidos.setDtalteracao(data);
                itensVendidosDAO.save(itensVendidos);

                p.setQuantidade(p.getQuantidade() - itensVendidos.getQuantidadeVenda());
                produtosDAO.save(p);

                vTotal += itensVendidos.getQuantidadeVenda() * itensVendidos.getPrecoVenda();
            }

            double totalfinal = vTotal;
            v.setValorParcela(totalfinal);
            vendasdao.save(v);

            for (int i = 0; i < Integer.parseInt(parcelas); i++) {
                ContasReceber contasReceber = new ContasReceber();

                contasReceber.setClienteJur(v.getClienteJur());
                contasReceber.setVendas(v);
                contasReceber.setNumeroParcelas(i + 1);
                double total = ((vTotal / Integer.parseInt(parcelas)));
                contasReceber.setValorParcela(total);

                Calendar c = new GregorianCalendar();
                c.setTime(data);
                c.add(Calendar.DATE, (30 * i));
                Date dataParcela = c.getTime();
                contasReceber.setDtParcela(dataParcela);

                contasReceber.setDtcriacao(data);
                contasReceber.setDtalteracao(data);

                contasReceberDAO.save(contasReceber);
            }


            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/consulta")
    public String consultaVendas() {
        return ("consultaVendas");
    }

    @RequestMapping(value = "/consulta/lista", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONArray> consultarVendas() {
        try {
            JSONObject j = new JSONObject();
            JSONArray a = new JSONArray();

            List<Vendas> vendas = vendasdao.findAll();

            for (int i = 0; i < vendas.size(); i++) {
                j.put("idvenda", vendas.get(i).getId());
                j.put("nf", vendas.get(i).getNf());
                ClienteFis cf = vendas.get(i).getClienteFis();
                if (cf != null) {
                    j.put("cliente", cf.getNome());
                    j.put("codigo", cf.getCpf());
                } else {
                    j.put("cliente", vendas.get(i).getClienteJur().getNomeFant());
                    j.put("codigo", vendas.get(i).getClienteJur().getCnpj());
                }
                j.put("total", vendas.get(i).getValorParcela());
                j.put("datavenda", vendas.get(i).getDtVenda());
                j.put("parcelas", vendas.get(i).getNumeroParcelas());
                a.add(j);
            }

            return new ResponseEntity<JSONArray>(a, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/editar", method = RequestMethod.POST, produces = "application/json")
    public void editarVendas(
            @JsonProperty(value = "id") String id,
            @JsonProperty(value = "tipoCodigo") String tipoCodigo,
            @JsonProperty(value = "codigo") String codigo,
            @JsonProperty(value = "nf") String nf,
            @JsonProperty(value = "parcelas") String parcelas
    ) {
        try {
            Vendas v = vendasdao.findOneById(id);

            List<ContasReceber> contasRecebers = contasReceberDAO.findAllByVendas(v);
            for(int i = 0; i < contasRecebers.size(); i++){
                contasReceberDAO.delete(contasRecebers.get(i));
            }
            v.setNf(nf);
            v.setNumeroParcelas(Integer.parseInt(parcelas));

            if(tipoCodigo.equals("CPF")){
                v.setClienteJur(null);
                v.setClienteFis(clientefisDAO.findFirstByCpf(codigo));
                vendasdao.save(v);

                for (int i = 0; i < Integer.parseInt(parcelas); i++) {
                    ContasReceber contasReceber = new ContasReceber();

                    contasReceber.setClienteFis(v.getClienteFis());
                    contasReceber.setVendas(v);
                    contasReceber.setNumeroParcelas(i + 1);
                    double total = ((v.getValorParcela() / Integer.parseInt(parcelas)));
                    contasReceber.setValorParcela(total);

                    Date data = v.getDtcriacao();
                    Calendar c = new GregorianCalendar();
                    c.setTime(data);
                    c.add(Calendar.DATE, (30 * i));
                    Date dataParcela = c.getTime();
                    contasReceber.setDtParcela(dataParcela);

                    contasReceber.setDtcriacao(data);
                    contasReceber.setDtalteracao(data);

                    contasReceberDAO.save(contasReceber);
                }
            }
            else if(tipoCodigo.equals("CNPJ")){
                v.setClienteFis(null);
                v.setClienteJur(clientejurDAO.findFirstByCnpj(codigo));
                vendasdao.save(v);

                for (int i = 0; i < Integer.parseInt(parcelas); i++) {
                    ContasReceber contasReceber = new ContasReceber();

                    contasReceber.setClienteJur(v.getClienteJur());
                    contasReceber.setVendas(v);
                    contasReceber.setNumeroParcelas(i + 1);
                    double total = ((v.getValorParcela() / Integer.parseInt(parcelas)));
                    contasReceber.setValorParcela(total);

                    Date data = v.getDtcriacao();
                    Calendar c = new GregorianCalendar();
                    c.setTime(data);
                    c.add(Calendar.DATE, (30 * i));
                    Date dataParcela = c.getTime();
                    contasReceber.setDtParcela(dataParcela);

                    contasReceber.setDtcriacao(data);
                    contasReceber.setDtalteracao(data);

                    contasReceberDAO.save(contasReceber);
                }
            }

        } catch (Exception e) {
            throw e;

        }
    }

    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
    public void excluirVenda(
            @JsonProperty(value = "id")String id
    ){
        try{
            Vendas v = vendasdao.findOneById(id);

            List<ItensVendidos> itensVendidos = itensVendidosDAO.findAllByVendas(v);

            for(int i = 0; i < itensVendidos.size(); i++){
                Produtos p = itensVendidos.get(i).getProdutos();
                p.setQuantidade(p.getQuantidade()+itensVendidos.get(i).getQuantidadeVenda());
                produtosDAO.save(p);
                itensVendidosDAO.delete(itensVendidos.get(i));
            }

            List<ContasReceber> contasRecebers = contasReceberDAO.findAllByVendas(v);
            for(int i = 0;i < contasRecebers.size(); i++){
                contasReceberDAO.delete(contasRecebers.get(i));
            }

            vendasdao.delete(v);


        }catch(Exception e){
            throw e;
        }

    }


}
