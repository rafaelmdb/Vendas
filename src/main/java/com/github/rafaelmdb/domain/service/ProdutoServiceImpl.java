package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.api.dto.PessoaDTO;
import com.github.rafaelmdb.domain.exception.RegraNegocioException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaServiceImpl implements ProdutoService {
    private final PessoaRepo pessoaRepo;

    public PessoaServiceImpl(PessoaRepo pessoaRepo) {
        this.pessoaRepo = pessoaRepo;
    }

    @Override
    public Pessoa criar(PessoaDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setIdade(dto.getIdade());
        pessoa.setCidade(dto.getCidade());
        pessoa.setNomePai(dto.getNomePai());
        pessoa.setNomeMae(dto.getNomeMae());

        return pessoaRepo.save(pessoa);
    }

    @Override
    public Pessoa alterar(UUID id, PessoaDTO dto) {
        return pessoaRepo.findById(id)
                .map(pessoa -> {
                    pessoa.setCidade(dto.getCidade());
                    pessoa.setDataNascimento(dto.getDataNascimento());
                    pessoa.setIdade(dto.getIdade());
                    pessoa.setCidade(dto.getCidade());
                    pessoa.setNome(dto.getNome());
                    pessoa.setNomeMae(dto.getNomeMae());
                    pessoa.setNomePai(dto.getNomePai());

                    pessoaRepo.save(pessoa);
                    return pessoa;
                })
                .orElseThrow(() -> new RegraNegocioException(String.format("Pessoa {0} não encontrada", dto.getNome())));
    }

    @Override
    public Optional<PessoaDTO> obter(UUID id) {
        return Optional.empty();
    }
}
