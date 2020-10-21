package com.github.rafaelmdb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private String codigo;
    private String referencia;
    private String descricao;
    private String estoque;
}
