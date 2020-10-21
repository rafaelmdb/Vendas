package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.dto.ProdutoDTO;
import com.github.rafaelmdb.domain.entity.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public interface ProdutoService {
    @Transactional
    List<Produto> criar(@Valid List<ProdutoDTO> dto);

    @Transactional
    Produto alterar(UUID id, ProdutoDTO dto);

    @Transactional
    void remover(UUID id);

    @Transactional(readOnly = true)
    Produto obterPorId(UUID id);
}
