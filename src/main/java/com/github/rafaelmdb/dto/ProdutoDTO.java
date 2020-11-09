package com.github.rafaelmdb.dto;

import com.github.rafaelmdb.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDTO extends BaseDto {
    private UUID id;
    private String codigo;
    private String referencia;
    private String descricao;
    private String estoque;
}
