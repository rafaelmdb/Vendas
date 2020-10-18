package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pessoa;
import com.github.rafaelmdb.domain.enums.TipoVinculoPessoa;
import com.github.rafaelmdb.domain.entity.VinculoPessoa;

public interface VinculoPessoaService {
    VinculoPessoa criar(Pessoa pessoa, Pessoa vinculo, TipoVinculoPessoa tipoVinculoPessoa);
    void DesfazerVinculo(Pessoa pessoa, Pessoa vinculo);
}
