package com.github.rafaelmdb.api.dto;

import com.github.rafaelmdb.domain.entity.ParticipanteSessao;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class SessaoDTO {
    private UUID id;
    private LocalDate data;
    private LocalTime hora;
    private String tipóSessao;
    private String nomeResponsavel;
    private UUID responsavelId;
    private Integer getMaximoPessoas = 0;
    private String anotacoes;

    private List<ParticipanteSessaoDTO> participantes = new ArrayList<ParticipanteSessaoDTO>();
}
