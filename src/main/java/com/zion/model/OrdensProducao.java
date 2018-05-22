package com.zion.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="ordensProducao")
public class OrdensProducao implements Serializable  {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name="itemvenda")
    private ItensVendidos itensVendidos;

    @Column(name = "quantidade")
    private Integer quantidade;

    @CreationTimestamp
    @Column(name = "dtproducao")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/mm/yyyy hh:mm:ss")
    private Date dtproducao;

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


    public OrdensProducao(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItensVendidos getItensVendidos() {
        return itensVendidos;
    }

    public void setItensVendidos(ItensVendidos itensVendidos) {
        this.itensVendidos = itensVendidos;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDtproducao() {
        return dtproducao;
    }

    public void setDtproducao(Date dtproducao) {
        this.dtproducao = dtproducao;
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
}
