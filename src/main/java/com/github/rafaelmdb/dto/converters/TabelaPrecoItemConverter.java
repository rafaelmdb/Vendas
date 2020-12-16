package com.github.rafaelmdb.dto.converters;

import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import com.github.rafaelmdb.domain.repo.ProdutoRepo;
import com.github.rafaelmdb.domain.repo.TabelaPrecoRepo;
import com.github.rafaelmdb.dto.TabelaPrecoItemDTO;
import com.github.rafaelmdb.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

@Component
public class TabelaPrecoItemConverter extends BaseConverter<TabelaPrecoItem, TabelaPrecoItemDTO>{
    private final ProdutoRepo produtoRepo;
    private final TabelaPrecoRepo tabelaPrecoRepo;

    public TabelaPrecoItemConverter(ProdutoRepo produtoRepo, TabelaPrecoRepo tabelaPrecoRepo){
        this.produtoRepo=produtoRepo;
        this.tabelaPrecoRepo = tabelaPrecoRepo;
    }

    @Override
    protected TabelaPrecoItem DoCreateFrom(TabelaPrecoItemDTO dto){
        TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();

        Produto produto = produtoRepo.findById(dto.getProdutoId())
                .orElseThrow(()-> new RegraNegocioException("produto.nao.encontrado"));

        TabelaPreco tabelaPreco = tabelaPrecoRepo.findById(dto.getTabelaPrecoId())
                .orElseThrow(()-> new RegraNegocioException("tabelapreco.nao.encontrada"));

        tabelaPrecoItem.setId(dto.getId());
        tabelaPrecoItem.setPreco(dto.getPreco());
        tabelaPrecoItem.setProduto(produto);
        tabelaPrecoItem.setTabelaPreco(tabelaPreco);
        return tabelaPrecoItem;
    }

    @Override
    protected TabelaPrecoItemDTO DoCreateFrom(TabelaPrecoItem tabelaPrecoItem){
        return TabelaPrecoItemDTO
                .builder()
                .id(tabelaPrecoItem.getId())
                .tabelaPrecoId(tabelaPrecoItem.getTabelaPreco().getId())
                .preco(tabelaPrecoItem.getPreco())
                .produtoId(tabelaPrecoItem.getProduto().getId())
                .build();
    }
}
