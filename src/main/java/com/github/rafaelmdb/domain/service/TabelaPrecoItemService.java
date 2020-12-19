package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public interface TabelaPrecoItemService {
    @Transactional
    TabelaPrecoItem adicionar(TabelaPrecoItem tabelaPrecoItem);

    @Transactional
    TabelaPrecoItem alterar(TabelaPrecoItem tabelaPrecoItem);

    @Transactional
    TabelaPrecoItem remover(UUID id);

    @Transactional(readOnly = true)
    TabelaPrecoItem obterPorId(UUID id);
}
