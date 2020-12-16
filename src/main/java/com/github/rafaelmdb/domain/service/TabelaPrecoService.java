package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public interface TabelaPrecoService {
    @Transactional
    TabelaPreco criar(TabelaPreco tabelaPreco);

    @Transactional
    TabelaPreco alterar(TabelaPreco tabelaPreco);

    @Transactional
    void remover(UUID id);

    @Transactional(readOnly = true)
    TabelaPreco obterPorId(UUID id);

    @Transactional
    TabelaPrecoItem adicionarItem(TabelaPrecoItem tabelaPrecoItem);
}
