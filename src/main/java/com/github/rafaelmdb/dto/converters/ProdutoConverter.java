package com.github.rafaelmdb.dto.converters;

import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.dto.ProdutoDTO;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ProdutoConverter extends BaseConverter<Produto, ProdutoDTO>{

    @Override
    protected Produto DoCreateFrom(ProdutoDTO dto){
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setReferencia(dto.getReferencia());
        produto.setCodigo(dto.getCodigo());
        produto.setEstoque(dto.getEstoque());
        produto.setDescricao(dto.getDescricao());
        produto.setId(dto.getId());
        return produto;
    }

    @Override
    protected ProdutoDTO DoCreateFrom(Produto produto){
        return ProdutoDTO
                .builder()
                .id(produto.getId())
                .codigo(produto.getCodigo())
                .descricao(produto.getDescricao())
                .estoque(produto.getEstoque())
                .referencia(produto.getReferencia())
                .build();
    }
}
