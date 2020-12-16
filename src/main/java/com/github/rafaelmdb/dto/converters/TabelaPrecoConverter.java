package com.github.rafaelmdb.dto.converters;

import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.domain.entity.TabelaPreco;
import com.github.rafaelmdb.dto.TabelaPrecoDTO;
import org.springframework.stereotype.Component;

@Component
public class TabelaPrecoConverter extends BaseConverter<TabelaPreco, TabelaPrecoDTO>{

    @Override
    protected TabelaPreco DoCreateFrom(TabelaPrecoDTO dto){
        TabelaPreco tabelaPreco = new TabelaPreco();
        tabelaPreco.setId(dto.getId());
        tabelaPreco.setNumero(dto.getNumero());
        tabelaPreco.setDataTabela(dto.getDataTabela());
        tabelaPreco.setUf(dto.getUf());
        tabelaPreco.setStatus(dto.getStatus());
        return tabelaPreco;
    }

    @Override
    protected TabelaPrecoDTO DoCreateFrom(TabelaPreco tabelaPreco){
        return TabelaPrecoDTO
                .builder()
                .id(tabelaPreco.getId())
                .dataTabela(tabelaPreco.getDataTabela())
                .numero(tabelaPreco.getNumero())
                .status(tabelaPreco.getStatus())
                .uf(tabelaPreco.getUf())
                .build();
    }
}
