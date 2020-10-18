package com.github.rafaelmdb.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PessoaDTO {
    private UUID id;
    private String nome;
    private LocalDate dataNascimento;
    private Integer idade;
    private String cidade;
    private String nomeMae;
    private String nomePai;
}
