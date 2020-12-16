package com.github.rafaelmdb.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.rafaelmdb.base.BaseDto;
import com.github.rafaelmdb.domain.enums.TipoPessoa;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO extends BaseDto {
    private UUID id;
    private String nome;
    private String cnpjcpf;
    private TipoPessoa tipoPessoa;
    private String email;
    private String telefone;
}
