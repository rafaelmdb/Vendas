package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.base.BaseEntity;
import com.github.rafaelmdb.domain.enums.TipoPessoa;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Entity
@Table(name="CLIENTE")
public class Cliente extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    private UUID id;

    @Column(name="NOME", length = 255)
    @NotEmpty(message = "Informe o nome do cliente")
    private String nome;

    @Column(name="CNPJCPF", length = 14)
    private String cnpjCpf;

    @Column(name="TIPOPESSOA")
    private TipoPessoa tipoPessoa;

    @Column(name="EMAIL", length = 255)
    private String email;

    @Column(name="TELEFONE", length = 255)
    private String telefone;

    public Cliente(){

        this.id = UUID.randomUUID();
        this.setTipoPessoa(TipoPessoa.JURIDICA);
    }
}
