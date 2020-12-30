package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.base.BaseEntity;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import com.github.rafaelmdb.domain.enums.TipoPessoa;
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
@Table(name="PEDIDO")
public class Pedido extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    @NotNull
    private UUID id;

    @Column(name="NUMERO", length = 255)
    @NotNull
    private String numero;

    @ManyToOne()
    @JoinColumn(name="CLIENTEID")
    @Type(type="uuid-char")
    @NotNull
    private Cliente cliente;

    @Column(name="DATAEMISSAO")
    @NotNull
    private LocalDate dataEmissao;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusPedido status;

    @Column(name="VALORTOTALIPI")
    @NotNull
    private BigDecimal valorTotalIPI;

    @Column(name="VALORTOTALDESCONTO")
    @NotNull
    private BigDecimal valorTotalDesconto;

    @Column(name="VALORTOTALBRUTO")
    @NotNull
    private BigDecimal valorTotalBruto;

    @Column(name="VALORTOTALLIQUIDO")
    @NotNull
    private BigDecimal valorTotalLiquido;

    @Column(name="VALORTOTALPEDIDO")
    @NotNull
    private BigDecimal valorTotalPedido;

    public Pedido(){
        this.id = UUID.randomUUID();
        this.setStatus(StatusPedido.RASCUNHO);
    }
}
