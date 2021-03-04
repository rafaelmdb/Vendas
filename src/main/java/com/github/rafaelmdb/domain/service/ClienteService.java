package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
public interface ClienteService {
    @Transactional
    Cliente criar(@Valid Cliente cliente);

    @Transactional
    Cliente alterar(@Valid Cliente cliente);

    @Transactional
    void remover(UUID id);

    @Transactional(readOnly = true)
    Cliente obterPorId(UUID id);
}
