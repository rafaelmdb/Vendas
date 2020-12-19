package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.UF;
import com.github.rafaelmdb.domain.repo.UFRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UFServiceImpl extends BaseService implements UFService {
    private final UFRepo uFRepo;

    public UFServiceImpl(MessageService messageService, UFRepo uFRepo){
        super(messageService);
        this.uFRepo = uFRepo;
    }

    @Override
    public UF criar(UF uF){
        validarUF(uF);
        return uFRepo.save(uF);
    }

    private void validarUF(UF uF){
        getMessageService().validar(uF.getSigla()==null, "sigla.uf.nao.informada");
    }

    @Override
    public UF alterar(UF uF) {
        validarAlteracao(uF.getId(), uFRepo);
        validarUF(uF);
        return uFRepo.save(uF);
    }

    public UF obterPorId(UUID id) {
        return uFRepo
                .findById(id)
                .orElseThrow(()->new RegraNegocioException(getMessageService().getMessage("uf.nao.encontrada", null)));
    }

    @Override
    public void remover(UUID id) {
        UF uF = obterPorId(id);
        uFRepo.delete(uF);
    }
}
