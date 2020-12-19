package com.github.rafaelmdb.domain.service;

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
public class TabelaPrecoServiceImpl extends BaseService implements TabelaPrecoService {
    private final TabelaPrecoRepo tabelaPrecoRepo;

    public TabelaPrecoServiceImpl(MessageService messageService, TabelaPrecoRepo tabelaPrecoRepo){
        super(messageService);
        this.tabelaPrecoRepo = tabelaPrecoRepo;
    }

    @Override
    public TabelaPreco criar(TabelaPreco tabelaPreco){
        validarTabelaPreco(tabelaPreco);
        return tabelaPrecoRepo.save(tabelaPreco);
    }

    private void validarTabelaPreco(TabelaPreco tabelaPreco){
        getMessageService().validar(tabelaPreco.getNumero()==null, "produto.nao.informado");
        getMessageService().validar(tabelaPreco.getDataTabela()==null, "data.nao.informada");
        getMessageService().validar(tabelaPreco.getNumero()==null, "numero.nao.informado");
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
                .orElseThrow(()->new RegraNegocioException(getMessageService().getMessage("tabelapreco.nao.encontrada", null)));
    }

    @Override
    public void remover(UUID id) {
        TabelaPreco tabelaPreco = obterPorId(id);
        tabelaPrecoRepo.delete(tabelaPreco);
    }
}
