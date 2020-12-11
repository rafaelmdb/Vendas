package com.github.rafaelmdb.dto;

import com.github.rafaelmdb.base.BaseDto;
import com.github.rafaelmdb.domain.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
