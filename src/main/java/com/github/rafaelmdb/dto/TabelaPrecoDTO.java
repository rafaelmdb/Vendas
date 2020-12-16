package com.github.rafaelmdb.dto;

import com.github.rafaelmdb.base.BaseDto;
import com.github.rafaelmdb.domain.entity.UF;
import com.github.rafaelmdb.domain.enums.StatusTabelaPreco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TabelaPrecoDTO extends BaseDto {
    private UUID id;
    private UF uf;
    private LocalDate dataTabela;
    private Integer numero;
    private StatusTabelaPreco status;
}
