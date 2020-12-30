package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.base.BaseEntity;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    @NotNull
    @JoinColumn(name="pedidoid")
    private Pedido pedido;

    @ManyToOne()
    @JoinColumn(name="PRODUTOID")
    @Type(type="uuid-char")
    @NotNull
    private Produto produdo;

    @Column(name="QUANTIDADE")
    @NotNull
    private BigDecimal quantidade;

    @Column(name="PRECOBRUTO")
    @NotNull
    private BigDecimal precoBruto;

    @Column(name="PERCDESCONTOPRECO")
    @NotNull
    private BigDecimal percDescontoPreco;

    @Column(name="DESCONTOPRECO")
    @NotNull
    private BigDecimal descontoPreco;

    @Column(name="PRECOLIQUIDO")
    @NotNull
    private BigDecimal precoLiquido;

    @Column(name="PERCIPI")
    @NotNull
    private BigDecimal percIPI;

    @Column(name="VALORIPI")
    @NotNull
    private BigDecimal valorIPI;

    @Column(name="VALORTOTALBRUTO")
    @NotNull
    private BigDecimal valorTotalBruto;

    @Column(name="VALORDESCONTOTOTAL")
    @NotNull
    private BigDecimal valorDescontoTotal;

    @Column(name="PERCDESCONTOTOTAL")
    @NotNull
    private BigDecimal percDescontoTotal;

    @Column(name="VALORTOTALLIQUIDO")
    @NotNull
    private BigDecimal valorTotalLiquido;


    public PedidoItem(){
        this.id = UUID.randomUUID();
    }
}
