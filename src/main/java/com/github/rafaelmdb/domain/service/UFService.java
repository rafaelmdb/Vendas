package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.UF;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public interface UFService {
    @Transactional
    UF criar(UF uF);

    @Transactional
    UF alterar(UF uF);

    @Transactional
    void remover(UUID id);

    @Transactional(readOnly = true)
    UF obterPorId(UUID id);
}
