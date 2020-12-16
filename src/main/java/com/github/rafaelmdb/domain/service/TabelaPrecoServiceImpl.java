package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import com.github.rafaelmdb.domain.repo.TabelaPrecoItemRepo;
import com.github.rafaelmdb.domain.repo.TabelaPrecoRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TabelaPrecoServiceImpl extends BaseService implements TabelaPrecoService {
    @Autowired
    private final MessageService messageService;
    private final TabelaPrecoRepo tabelaPrecoRepo;
    private final TabelaPrecoItemRepo tabelaPrecoItemRepo;

    public TabelaPrecoServiceImpl(MessageService messageService, TabelaPrecoRepo tabelaPrecoRepo, TabelaPrecoItemRepo tabelaPrecoItemRepo){
        this.messageService = messageService;
        this.tabelaPrecoRepo = tabelaPrecoRepo;
        this.tabelaPrecoItemRepo = tabelaPrecoItemRepo;
    }

    @Override
    public TabelaPreco criar(TabelaPreco tabelaPreco){
        validarTabelaPreco(tabelaPreco);
        return tabelaPrecoRepo.save(tabelaPreco);
    }

    private void validarTabelaPreco(TabelaPreco tabelaPreco){
    }

    @Override
    public TabelaPreco alterar(TabelaPreco tabelaPreco) {
        validarAlteracao(tabelaPreco.getId(), tabelaPrecoRepo);
        validarTabelaPreco(tabelaPreco);
        return tabelaPrecoRepo.save(tabelaPreco);
    }

    public TabelaPreco obterPorId(UUID id) {
        return tabelaPrecoRepo
                .findById(id)
                .orElseThrow(()->new RegraNegocioException(messageService.getMessage("tabelapreco.nao.encontrada", null)));
    }

    @Override
    public TabelaPrecoItem adicionarItem(TabelaPrecoItem tabelaPrecoItem) {
        return tabelaPrecoItemRepo.save(tabelaPrecoItem);
    }

    @Override
    public void remover(UUID id) {
        TabelaPreco tabelaPreco = obterPorId(id);
        tabelaPrecoRepo.delete(tabelaPreco);
    }
}
