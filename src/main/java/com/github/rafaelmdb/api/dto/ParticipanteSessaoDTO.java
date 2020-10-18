package com.github.rafaelmdb.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParticipanteSessaoDTO {
    private UUID participanteId;
    private UUID pessoaId;
    private String nomePessoa;
}
