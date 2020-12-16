package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.UF;
import com.github.rafaelmdb.domain.repo.UFRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UFServiceImpl extends BaseService implements UFService {
    @Autowired
    private final MessageService messageService;
    private final UFRepo uFRepo;

    public UFServiceImpl(MessageService messageService, UFRepo uFRepo){
        this.messageService = messageService;
        this.uFRepo = uFRepo;
    }

    @Override
    public UF criar(UF uF){
        validarUF(uF);
        return uFRepo.save(uF);
    }

    private void validarUF(UF uF){
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
                .orElseThrow(()->new RegraNegocioException(messageService.getMessage("uf.nao.encontrada", null)));
    }

    @Override
    public void remover(UUID id) {
        UF uF = obterPorId(id);
        uFRepo.delete(uF);
    }
}
