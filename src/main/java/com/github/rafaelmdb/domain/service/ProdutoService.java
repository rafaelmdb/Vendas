package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public interface ProdutoService {
    @Transactional
    Produto criar(Produto produto);

    @Transactional
    Produto alterar(UUID id, Produto produto);

    @Transactional
    void remover(UUID id);

    @Transactional(readOnly = true)
    Produto obterPorId(UUID id);
}
