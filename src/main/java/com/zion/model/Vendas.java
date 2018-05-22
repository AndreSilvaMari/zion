package com.zion.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="vendas")
public class Vendas implements Serializable  {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name="clientefis")
    private ClienteFis clienteFis;

    @CreationTimestamp
    @Column(name = "dtvenda")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/mm/yyyy hh:mm:ss")
    private Date dtVenda;

    @Column(name = "valorparcela")
    private Double valorParcela;

    @Column(name = "numeroparcelas")
    private Integer numeroParcelas;

    @Column(name = "nf")
    private String nf;

    @CreationTimestamp
    @Column(name = "dtcriacao")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/mm/yyyy hh:mm:ss")
    private Date dtcriacao;

    @UpdateTimestamp
    @Column(name = "dtalteracao")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/mm/yyyy hh:mm:ss")
    private Date dtalteracao;

    @ManyToOne
    @JoinColumn(name="clientejur")
    private ClienteJur clienteJur;

    public Vendas(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClienteFis getClienteFis() {
        return clienteFis;
    }

    public void setClienteFis(ClienteFis clienteFis) {
        this.clienteFis = clienteFis;
    }

    public Date getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(Date dtVenda) {
        this.dtVenda = dtVenda;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public Date getDtcriacao() {
        return dtcriacao;
    }

    public void setDtcriacao(Date dtcriacao) {
        this.dtcriacao = dtcriacao;
    }

    public Date getDtalteracao() {
        return dtalteracao;
    }

    public void setDtalteracao(Date dtalteracao) {
        this.dtalteracao = dtalteracao;
    }

    public ClienteJur getClienteJur() {
        return clienteJur;
    }

    public void setClienteJur(ClienteJur clienteJur) {
        this.clienteJur = clienteJur;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }
}
