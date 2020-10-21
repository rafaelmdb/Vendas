package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.domain.enums.StatusTabelaPreco;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="TABELAPRECO")
public class TabelaPreco {
    @Id
    @GeneratedValue(generator = "UUID")
    /*@GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")*/
    @Column(name = "ID")
    @Type(type="uuid-char")
    private UUID id;


    @ManyToOne()
    @JoinColumn(name="UFID")
    @Type(type="uuid-char")
    private UF uf;

    @Column(name="DATATABELA")
    @NotNull
    private LocalDate dataTabela;

    @Column(name="NUMERO")
    @NotNull
    private Integer numero;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusTabelaPreco status;

    public TabelaPreco(){
        this.id= UUID.randomUUID();
        this.status=StatusTabelaPreco.ATIVA;
    }

    public TabelaPreco(LocalDate dataTabela, Integer numero, StatusTabelaPreco status){
        this();
        this.dataTabela=dataTabela;
        this.numero=numero;
        this.status=status;
    }
}
