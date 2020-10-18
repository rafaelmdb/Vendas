package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pessoa;
import com.github.rafaelmdb.domain.enums.TipoVinculoPessoa;
import com.github.rafaelmdb.domain.entity.VinculoPessoa;
import com.github.rafaelmdb.domain.repo.VinculoPessoaRepo;
import org.springframework.stereotype.Service;

@Service
public class VinculoPessoaServiceImpl implements VinculoPessoaService{
    private final VinculoPessoaRepo vinculoPessoaRepo;

    public VinculoPessoaServiceImpl(VinculoPessoaRepo vinculoPessoaRepo){
        this.vinculoPessoaRepo = vinculoPessoaRepo;
    }
    public VinculoPessoa criar(Pessoa pessoa, Pessoa vinculo, TipoVinculoPessoa tipoVinculoPessoa){
        VinculoPessoa vinculoPessoa = new VinculoPessoa(pessoa, vinculo, tipoVinculoPessoa);
        return vinculoPessoaRepo.save(vinculoPessoa);
    }

    public void DesfazerVinculo(Pessoa pessoa, Pessoa vinculo){
        return;
    }
}
