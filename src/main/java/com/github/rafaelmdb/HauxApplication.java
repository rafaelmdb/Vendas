package com.github.rafaelmdb;

import com.github.rafaelmdb.api.dto.PessoaDTO;
import com.github.rafaelmdb.api.dto.SessaoDTO;
import com.github.rafaelmdb.domain.entity.Pessoa;
import com.github.rafaelmdb.domain.entity.Sessao;
import com.github.rafaelmdb.domain.enums.TipoVinculoPessoa;
import com.github.rafaelmdb.domain.entity.VinculoPessoa;
import com.github.rafaelmdb.domain.repo.VinculoPessoaRepo;
import com.github.rafaelmdb.domain.service.PessoaService;
import com.github.rafaelmdb.domain.service.SessaoService;
import com.github.rafaelmdb.domain.service.VinculoPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class HauxApplication {
    public static void main(String[] args) {
        SpringApplication.run(HauxApplication.class, args);
    }

    @Autowired
    PessoaService pessoaService;

    @Autowired
    VinculoPessoaService vinculoService;

    @Autowired
    VinculoPessoaRepo vinculoRepo;

    @Autowired
    SessaoService sessaoService;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            PessoaDTO pessoa = new PessoaDTO();

            pessoa.setNome("Rafael");
            pessoa.setNomeMae("Albertina");
            pessoa.setNomePai("Arlindo");
            pessoa.setCidade("Criciúma");
            pessoa.setDataNascimento(LocalDate.of(1982,12,12));
            Pessoa pai = pessoaService.criar(pessoa);

            pessoa = new PessoaDTO();
            pessoa.setNome("Raul");
            pessoa.setNomePai("Rafael");
            pessoa.setNomeMae("Morgana");
            Pessoa filho = pessoaService.criar(pessoa);

            pessoa = new PessoaDTO();
            pessoa.setNome("Morgana");
            Pessoa esposa = pessoaService.criar(pessoa);

            vinculoService.criar(pai, filho, TipoVinculoPessoa.FILHO);

            vinculoService.criar(pai, esposa, TipoVinculoPessoa.CONJUGUE);
            vinculoService.criar(esposa, filho, TipoVinculoPessoa.FILHO);

            Optional<VinculoPessoa> vinculo = vinculoRepo.findVinculo(pai, filho);

            SessaoDTO dto = new SessaoDTO();
            dto.setAnotacoes("Minhas notas");
            dto.setData(LocalDate.now());
            dto.setResponsavelId(pai.getId());
            dto.setTipóSessao("ESTUDO");
            Sessao sessao = sessaoService.criar(dto);
            sessaoService.adicionarParticipantes(sessao.getId(), Arrays.asList(pai));
        };
    }
}

