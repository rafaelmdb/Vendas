package com.github.rafaelmdb.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name="TABELAPRECOITEM")
public class TabelaPrecoItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name="TABELAPRECOID")
    @Type(type="uuid-char")
    private TabelaPreco tabelaPreco;

    @ManyToOne()
    @JoinColumn(name="PRODUTOID")
    @Type(type="uuid-char")
    private Produto produto;

    @Column(name="PRECO")
    @NotNull
    private BigDecimal preco;

    public TabelaPrecoItem(){}
    public TabelaPrecoItem(TabelaPreco tabelaPreco, Produto produto, BigDecimal preco){
        this();
        this.tabelaPreco=tabelaPreco;
        this.produto=produto;
        this.preco=preco;
    }
}
