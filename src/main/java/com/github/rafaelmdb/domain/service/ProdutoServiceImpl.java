package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.repo.ProdutoRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ProdutoServiceImpl extends BaseService implements ProdutoService {
    private final ProdutoRepo produtoRepo;

    public ProdutoServiceImpl(MessageService messageService, ProdutoRepo produtoRepo){
        super(messageService);
        this.produtoRepo = produtoRepo;
    }

    @Override
    public Produto criar(Produto produto){
        validarProduto(produto);
        return produtoRepo.save(produto);
    }

    private void validarProduto(Produto produto){
        getMessageService().validar(Strings.isEmpty(produto.getDescricao()), "descricao.obrigatoria");
    }

    @Override
    public Produto alterar(Produto produto) {
        validarAlteracao(produto.getId(), produtoRepo);
        validarProduto(produto);
        return produtoRepo.save(produto);
    }

    public Produto obterPorId(UUID id) {
        return produtoRepo
                .findById(id)
                .orElseThrow(()->new RegraNegocioException(getMessageService().getMessage("produto.nao.encontrado", null)));
    }

    @Override
    public void remover(UUID id) {
        Produto produto = obterPorId(id);
        produtoRepo.delete(produto);
    }
}
