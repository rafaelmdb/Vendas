package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.dto.ProdutoDTO;
import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.exception.RegraNegocioException;
import com.github.rafaelmdb.domain.repo.ProdutoRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    private MessageService messageService;

    private final ProdutoRepo produtoRepo;
    public ProdutoServiceImpl(ProdutoRepo produtoRepo){
        this.produtoRepo = produtoRepo;
    }

    @Override
    public List<Produto> criar(List<ProdutoDTO> dto){
        List<Produto> produtos = new ArrayList<Produto>();

        dto.forEach(produtoDTO ->{
            Produto produto = new Produto(produtoDTO.getCodigo(), produtoDTO.getReferencia(), produtoDTO.getDescricao(), produtoDTO.getEstoque());
            validarProduto(produto);
            produtos.add(produto);
        });

        return produtoRepo.saveAll(produtos);
    }

    private void validarProduto(Produto produto){
        if(Strings.isEmpty(produto.getDescricao())){
            throw new RegraNegocioException(messageService.getMessage("descricao.obrigatoria",null));
        }
    }

    @Override
    public Produto alterar(UUID id, ProdutoDTO dto) {
        Produto produto = this.obterPorId(id);
        produto.setCodigo(dto.getCodigo());
        produto.setReferencia(dto.getReferencia());
        produto.setDescricao(dto.getDescricao());
        produto.setEstoque(dto.getEstoque());

        return produtoRepo.save(produto);
    }

    public Produto obterPorId(UUID id) {
        return produtoRepo
                .findById(id)
                .orElseThrow(()->new RegraNegocioException("produto.nao.encontrado"));
    }

    @Override
    public void remover(UUID id) {
        Produto produto = obterPorId(id);
        produtoRepo.delete(produto);
    }
}
