package com.github.rafaelmdb.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name="PARTICIPANTESESSAO")
public class ParticipanteSessao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",
            strategy ="org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    @Type(type="uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="SESSAOID")
    @Type(type="uuid-char")
    @NotNull
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name="PESSOAID")
    @Type(type="uuid-char")
    @NotNull
    private Pessoa pessoa;

    @Column(name="PAGO")
    @NotNull
    private Boolean pago;

    @Column(name="VALORPAGO")
    @NotNull
    private BigDecimal valorPago;

    @Column(name="COMPARECEU")
    @NotNull
    private Boolean compareceu;

    @Column(name="AVISOUFALTA")
    @NotNull
    private Boolean avisouFalta;

    @Column(name="AVISOUPRESENCA")
    @NotNull
    private Boolean avisouPresenca;

    public ParticipanteSessao(){
        this.pago = false;
        this.valorPago = BigDecimal.valueOf(0);
        this.compareceu = false;
        this.avisouFalta = false;
        this.avisouPresenca = false;

    }
    public ParticipanteSessao(Sessao sessao, Pessoa pessoa){
        this();
        this.sessao = sessao;
        this.pessoa = pessoa;
    }
}
