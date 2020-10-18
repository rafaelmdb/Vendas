package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.api.dto.PessoaDTO;
import com.github.rafaelmdb.domain.entity.Pessoa;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface PessoaService {
    @Transactional
    Pessoa criar(PessoaDTO dto);

    @Transactional
    Pessoa alterar(UUID id, PessoaDTO dto);

    @Transactional(readOnly = true)
    Optional<PessoaDTO> obter(UUID id);
}
