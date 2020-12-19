package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import com.github.rafaelmdb.domain.repo.TabelaPrecoItemRepo;
import com.github.rafaelmdb.domain.repo.TabelaPrecoRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TabelaPrecoItemServiceImpl extends BaseService implements TabelaPrecoItemService {
    @Autowired
    private final TabelaPrecoItemRepo tabelaPrecoItemRepo;

    public TabelaPrecoItemServiceImpl(MessageService messageService, TabelaPrecoItemRepo tabelaPrecoItemRepo){
        super(messageService);
        this.tabelaPrecoItemRepo = tabelaPrecoItemRepo;
    }

    public void validarTabelaPrecoItem(TabelaPrecoItem tabelaPrecoItem){
        getMessageService().validar(tabelaPrecoItem.getPreco()==null, "preco.nao.informado");
        getMessageService().validar(tabelaPrecoItem.getProduto()==null, "produto.nao.informado");
        getMessageService().validar(tabelaPrecoItem.getTabelaPreco()==null, "tabelapreco.nao.informada");
    }

    @Override
    public TabelaPrecoItem adicionar(TabelaPrecoItem tabelaPrecoItem) {
        validarAlteracao(tabelaPrecoItem.getId(), tabelaPrecoItemRepo);
        validarTabelaPrecoItem(tabelaPrecoItem);
        return tabelaPrecoItemRepo.save(tabelaPrecoItem);
    }

    @Override
    public TabelaPrecoItem alterar(TabelaPrecoItem tabelaPrecoItem) {
        validarTabelaPrecoItem(tabelaPrecoItem);
        return tabelaPrecoItemRepo.save(tabelaPrecoItem);
    }

    @Override
    public TabelaPrecoItem remover(UUID id) {
        return tabelaPrecoItemRepo
                .findById(id)
                .orElseThrow(()->new RegraNegocioException(getMessageService().getMessage("tabelapreco.nao.encontrada", null)));
    }

    public TabelaPrecoItem obterPorId(UUID id) {
        return tabelaPrecoItemRepo
                .findById(id)
                .orElseThrow(()->new RegraNegocioException(getMessageService().getMessage("tabelaprecoitem.nao.encontrado", null)));
    }

}
