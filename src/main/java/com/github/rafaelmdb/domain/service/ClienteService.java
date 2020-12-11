package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public interface ClienteService {
    @Transactional
    Cliente criar(Cliente cliente);

    @Transactional
    Cliente alterar(Cliente cliente);

    @Transactional
    void remover(UUID id);

    @Transactional(readOnly = true)
    Cliente obterPorId(UUID id);
}
