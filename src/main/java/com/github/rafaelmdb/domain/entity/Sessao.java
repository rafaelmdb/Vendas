package com.github.rafaelmdb.domain.entity;

import com.github.rafaelmdb.domain.enums.StatusSessao;
import com.github.rafaelmdb.domain.enums.TipoSessao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="SESSAO")
public class Sessao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    protected UUID id;

    @Column(name="DATA")
    @NotNull
    private LocalDate data;

    @Column(name="HORA")
    private LocalTime hora;

    @Column(name="TIPOSESSAO")
    @NotNull
    private TipoSessao tipoSessao;

    @ManyToOne
    @JoinColumn(name="RESPONSAVELID")
    @Type(type="uuid-char")
    @NotNull
    private Pessoa responsavel;

    @Column(name="MAXIMOPESSOAS")
    @NotNull
    private Integer maximoPessoas;

    @Column(name="ANOTACOES")
    private String anotacoes;

    @Column(name="STATUS")
    @NotNull
    private StatusSessao status;

    @OneToMany(mappedBy = "sessao")
    private List<ParticipanteSessao> participantes;

    public Sessao(LocalDate data, LocalTime hora, TipoSessao tipoSessao, Pessoa responsavel, Integer maximoPessoas, String anotacoes){
        this.data=data;
        this.hora=hora;
        this.tipoSessao=tipoSessao;
        this.responsavel=responsavel;
        this.maximoPessoas=maximoPessoas;
        this.anotacoes=anotacoes;
        this.status=StatusSessao.ABERTA;
    }

}
