package com.github.rafaelmdb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="PESSOA")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pessoa {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    private UUID id;

    @Column(name="NOME")
    @NotNull
    private String Nome;

    @Column(name="DATANASCIMENTO")
    private LocalDate DataNascimento;

    @Column(name="IDADE")
    private Integer idade;

    @Column(name="CIDADE")
    private String cidade;

    @Column(name="NOMEPAI")
    private String nomePai;

    @Column(name="NOMEMAE")
    private String nomeMae;

}
