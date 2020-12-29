package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Data
@Getter
@Table(name="PRODUTO")
public class Produto extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    private UUID id;

    @Column(name="CODIGO")
    private String codigo;

    @Column(name="REFERENCIA")
    private String referencia;

    @Column(name="DESCRICAO")
    @NotEmpty()
    private String descricao;

    @Column(name="ESTOQUE")
    private String estoque;

    public Produto(){}

    public Produto(String codigo, String referencia, String descricao,String estoque){
        this();
        this.codigo=codigo;
        this.referencia=referencia;
        this.descricao=descricao;
        this.estoque=estoque;
    }

}
