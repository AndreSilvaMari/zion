package com.zion.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.regexp.internal.RE;
import com.zion.dao.*;
import com.zion.model.*;

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

import javax.xml.ws.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
@RequestMapping(value = "/compras")
public class ComprasController {

    @Autowired
    private ComprasDAO comprasdao;

    @Autowired
    private FornecedoresDAO fornecedoresDAO;

    @Autowired
    private ProdutosDAO produtosDAO;

    @Autowired
    private ItensCompradosDAO itensCompradosDAO;

    @Autowired
    private ContasPagarDAO contasPagarDAO;

    @RequestMapping
    public String compra(){
        return ("compras");
    }

    @RequestMapping(value = "/consultar")
    public ResponseEntity<JSONObject> consultarCNPJ(
            @JsonProperty(value = "CNPJ") String CNPJ
    ) {
        try {
            Fornecedores f = fornecedoresDAO.findFirstByCnpj(CNPJ);

            JSONObject j = JSONObject.fromObject(f);

            return new ResponseEntity<JSONObject>(j, HttpStatus.OK);

        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/finalizar", method = RequestMethod.POST)
    public ResponseEntity finalizar(
            @JsonProperty(value = "codigo")String codigo,
            @JsonProperty(value = "prods")String prods,
            @JsonProperty(value = "parcelas")String parcelas,
            @JsonProperty(value = "nf")String nf
    ){
        try{

            prods = prods.replace("[", "");
            prods = prods.replace("]", "");
            prods = prods.replace("\"", "");
            prods = prods.replace("\\", "");

            String[] produtos = prods.split(",");

            Compras c = new Compras();

            c.setFornecedores(fornecedoresDAO.findFirstByCnpj(codigo));
            Date date = new Date();
            c.setDtcriacao(date);
            c.setDtcompra(date);
            c.setDtalteracao(date);
            c.setNf(nf);
            c.setParcelas(Integer.parseInt(parcelas));

            comprasdao.save(c);

            int cTotal = 0;

            for( int i = 0; i < produtos.length; i++){
                ItensComprados itensComprados = new ItensComprados();

                String codProd = produtos[i];
                String qtdProd = codProd.substring(40, codProd.length());
                qtdProd = qtdProd.replace("<", "");
                qtdProd = qtdProd.replace(">", "");
                qtdProd = qtdProd.replace("p", "");
                qtdProd = qtdProd.replace("/", "");
                codProd = codProd.substring(7, 39);

                Produtos p = produtosDAO.findOneById(codProd);

                itensComprados.setPrecoCompra(p.getPreco());
                itensComprados.setQuantidadeCompra(Integer.parseInt(qtdProd));
                itensComprados.setCompra(c);
                itensComprados.setProdutos(p);
                itensComprados.setDtcriacao(date);
                itensComprados.setDtalteracao(date);

                itensCompradosDAO.save(itensComprados);

                p.setQuantidade(p.getQuantidade()+itensComprados.getQuantidadeCompra());
                produtosDAO.save(p);

                cTotal += itensComprados.getQuantidadeCompra() * itensComprados.getPrecoCompra();
            }

            double totalfinal = cTotal;
            c.setValorParcela(totalfinal);
            comprasdao.save(c);

            for(int i = 0; i < Integer.parseInt(parcelas); i++){
                ContasPagar contasPagar = new ContasPagar();

                contasPagar.setFornecedores(c.getFornecedores());
                contasPagar.setCompras(c);
                contasPagar.setNumeroParcelas(i+1);
                double total = ((cTotal / Integer.parseInt(parcelas)));
                contasPagar.setValorParcela(total);

                Calendar ca = new GregorianCalendar();
                ca.setTime(date);
                ca.add(Calendar.DATE, (30 * i));
                Date dataParcela = ca.getTime();
                contasPagar.setDtParcela(dataParcela);

                contasPagar.setDtcriacao(date);
                contasPagar.setDtalteracao(date);

                contasPagarDAO.save(contasPagar);

            }

            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            throw e;
        }

    }

    @RequestMapping(value = "/consulta")
    public String consultaCompras(){ return ("consultaCompras");}

    @RequestMapping(value = "/consulta/lista", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<JSONArray> consultarCompras(){
        try{
            JSONObject j = new JSONObject();
            JSONArray a = new JSONArray();

            List<Compras> compras = comprasdao.findAll();

            for (int i = 0; i < compras.size(); i++) {
                j.put("idcompra", compras.get(i).getId());
                j.put("nf", compras.get(i).getNf());
                Fornecedores f = compras.get(i).getFornecedores();

                j.put("cliente", f.getNomefant());
                j.put("codigo", f.getCnpj());

                j.put("total", compras.get(i).getValorParcela());
                j.put("datacompra", compras.get(i).getDtcompra());
                j.put("parcelas", compras.get(i).getParcelas());
                a.add(j);
            }

            return new ResponseEntity<JSONArray>(a, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/editar", method = RequestMethod.POST, produces = "application/json")
    public void editarCompras(
            @JsonProperty(value = "id") String id,
            @JsonProperty(value = "codigo") String codigo,
            @JsonProperty(value = "nf") String nf,
            @JsonProperty(value = "parcelas") String parcelas
    ){
        Compras c = comprasdao.findOneById(id);

        List<ContasPagar> contasPagars = contasPagarDAO.findAllByCompras(c);
        for(int i = 0; i < contasPagars.size(); i++){
            contasPagarDAO.delete(contasPagars.get(i));
        }
        c.setNf(nf);
        c.setParcelas(Integer.parseInt(parcelas));
        c.setFornecedores(fornecedoresDAO.findFirstByCnpj(codigo));

        comprasdao.save(c);

        for (int i = 0; i < Integer.parseInt(parcelas); i++) {
            ContasPagar contasPagar = new ContasPagar();

            contasPagar.setFornecedores(c.getFornecedores());
            contasPagar.setCompras(c);
            contasPagar.setNumeroParcelas(i+1);
            double total = ((c.getValorParcela() / Integer.parseInt(parcelas)));
            contasPagar.setValorParcela(total);

            Date data = c.getDtcriacao();
            Calendar ca = new GregorianCalendar();
            ca.setTime(data);
            ca.add(Calendar.DATE, (30 * i));
            Date dataParcela = ca.getTime();

            contasPagar.setDtParcela(dataParcela);

            contasPagar.setDtcriacao(data);
            contasPagar.setDtalteracao(data);

            contasPagarDAO.save(contasPagar);

        }
    }


    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
    public void excluirCompra(
            @JsonProperty(value = "id")String id
    ){
                try{
                    Compras c = comprasdao.findOneById(id);

                    List<ItensComprados> itensComprados = itensCompradosDAO.findAllByCompras(c);
                    for(int i = 0; i < itensComprados.size(); i++){
                        Produtos p = itensComprados.get(i).getProdutos();
                        p.setQuantidade(p.getQuantidade() - itensComprados.get(i).getQuantidadeCompra());
                        produtosDAO.save(p);
                        itensCompradosDAO.delete(itensComprados.get(i));
                    }

                    List<ContasPagar> contasPagars = contasPagarDAO.findAllByCompras(c);
                    for( int i = 0 ; i < contasPagars.size(); i++){
                        contasPagarDAO.delete(contasPagars.get(i));
                    }

                    comprasdao.delete(c);

                }catch (Exception e){
                    throw e;
                }
    }

}

