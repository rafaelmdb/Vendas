package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.base.BaseEntity;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name="PEDIDOITEM")
public class PedidoItem extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    @NotNull
    private UUID id;

    @ManyToOne()
    @JoinColumn(name="PRODUTOID")
    @Type(type="uuid-char")
    @NotNull
    private Produto produdo;

    @Column(name="QUANTIDADE")
    @NotNull
    private double quantidade;

    @Column(name="PRECOBRUTO")
    @NotNull
    private double precoBruto;

    @Column(name="PERCDESCONTOPRECO")
    @NotNull
    private double percDescontoPreco;

    @Column(name="DESCONTOPRECO")
    @NotNull
    private double descontoPreo;

    @Column(name="PRECOLIQUIDO")
    @NotNull
    private double precoLiquido;

    @Column(name="PERCIPI")
    @NotNull
    private double percIPI;

    @Column(name="VALORIPI")
    @NotNull
    private double valorIPI;

    @Column(name="VALORTOTALBRUTO")
    @NotNull
    private double valorTotalBruto;

    @Column(name="VALORTOTALLIQUIDO")
    @NotNull
    private double valorTotalLiquido;


    public PedidoItem(){
        this.id = UUID.randomUUID();
    }
}
