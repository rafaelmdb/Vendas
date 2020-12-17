package com.github.rafaelmdb.dto.converters;

import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.domain.entity.TabelaPrecoItem;
import com.github.rafaelmdb.domain.repo.ProdutoRepo;
import com.github.rafaelmdb.domain.repo.TabelaPrecoRepo;
import com.github.rafaelmdb.domain.service.MessageService;
import com.github.rafaelmdb.dto.TabelaPrecoItemDTO;
import com.github.rafaelmdb.exception.RegraNegocioException;
import org.springframework.stereotype.Component;

@Component
public class TabelaPrecoItemConverter extends BaseConverter<TabelaPrecoItem, TabelaPrecoItemDTO>{
    private final ProdutoRepo produtoRepo;
    private final TabelaPrecoRepo tabelaPrecoRepo;
    private final MessageService messageService;

    public TabelaPrecoItemConverter(ProdutoRepo produtoRepo, TabelaPrecoRepo tabelaPrecoRepo, MessageService messageService){
        this.produtoRepo=produtoRepo;
        this.tabelaPrecoRepo = tabelaPrecoRepo;
        this.messageService = messageService;
    }

    @Override
    protected TabelaPrecoItem DoCreateFrom(TabelaPrecoItemDTO dto){
        TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();

        if (dto.getProdutoId()!=null) {
            Produto produto = produtoRepo.findById(dto.getProdutoId())
                    .orElseThrow(() -> new RegraNegocioException(messageService.getMessage("produto.nao.encontrado", null)));

            tabelaPrecoItem.setProduto(produto);

        }

        if (dto.getTabelaPrecoId()!=null) {
            TabelaPreco tabelaPreco = tabelaPrecoRepo.findById(dto.getTabelaPrecoId())
                    .orElseThrow(() -> new RegraNegocioException(messageService.getMessage("tabelapreco.nao.encontrada", null)));

            tabelaPrecoItem.setTabelaPreco(tabelaPreco);
        }

        tabelaPrecoItem.setId(dto.getId());
        tabelaPrecoItem.setPreco(dto.getPreco());
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
