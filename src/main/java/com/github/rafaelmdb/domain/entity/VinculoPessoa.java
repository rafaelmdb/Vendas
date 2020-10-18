package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.domain.enums.TipoVinculoPessoa;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name="VINCULOPESSOA")
public class VinculoPessoa {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name="PESSOAID")
    @Type(type="uuid-char")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name="VINCULOID")
    @Type(type="uuid-char")
    private Pessoa vinculo;

    @Column(name="TIPOVINCULO")
    @Enumerated(EnumType.STRING)
    private TipoVinculoPessoa tipoVinculo;

    public VinculoPessoa(Pessoa pessoa, Pessoa vinculo, TipoVinculoPessoa tipoVinculoPessoa){
        this.pessoa=pessoa;
        this.vinculo=vinculo;
        this.tipoVinculo=tipoVinculoPessoa;
    }
}
