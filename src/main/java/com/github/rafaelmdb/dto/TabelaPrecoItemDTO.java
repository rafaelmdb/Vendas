package com.github.rafaelmdb.dto;

import com.github.rafaelmdb.base.BaseDto;
import com.github.rafaelmdb.base.BaseEntity;
import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.entity.TabelaPreco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TabelaPrecoItemDTO extends BaseDto {
    private UUID id;
    private UUID tabelaPrecoId;
    private UUID produtoId;
    private BigDecimal preco;
}
