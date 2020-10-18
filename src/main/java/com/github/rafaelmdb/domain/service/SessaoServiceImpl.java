package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.api.dto.SessaoDTO;
import com.github.rafaelmdb.domain.entity.ParticipanteSessao;
import com.github.rafaelmdb.domain.entity.Pessoa;
import com.github.rafaelmdb.domain.entity.Sessao;
import com.github.rafaelmdb.domain.enums.StatusSessao;
import com.github.rafaelmdb.domain.enums.TipoSessao;
import com.github.rafaelmdb.domain.exception.RegraNegocioException;
import com.github.rafaelmdb.domain.repo.ParticipanteSessaoRepo;
import com.github.rafaelmdb.domain.repo.PessoaRepo;
import com.github.rafaelmdb.domain.repo.SessaoRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SessaoServiceImpl implements SessaoService {
    private final SessaoRepo sessaoRepo;
    private final PessoaRepo pessoaRepo;
    private final ParticipanteSessaoRepo participanteSessaoRepo;

    public SessaoServiceImpl(SessaoRepo sessaoRepo, PessoaRepo pessoaRepo, ParticipanteSessaoRepo participanteSessaoRepo){
        this.sessaoRepo = sessaoRepo;
        this.pessoaRepo = pessoaRepo;
        this.participanteSessaoRepo = participanteSessaoRepo;
    }

    @Transactional
    @Override
    public Sessao criar(SessaoDTO dto) {
        Pessoa responsavel = obterResponsavel(dto.getResponsavelId(), String.format("{responsavel.nao.encontrado}", dto.getNomeResponsavel()));
        Sessao sessao = new Sessao(dto.getData(), dto.getHora(), TipoSessao.valueOf(dto.getTipóSessao()), responsavel, dto.getGetMaximoPessoas(), dto.getAnotacoes());

        if (!dataLivre(sessao)){
            throw new RegraNegocioException("{data.indisponivel}");
        }

        return sessaoRepo.save(sessao);
    }

    private Pessoa obterResponsavel(UUID responsavelId, String mensagensNaoEncontrado) {
        return pessoaRepo.findById(responsavelId)
                .orElseThrow(()->
                        new RegraNegocioException(mensagensNaoEncontrado));
    }

    private boolean dataLivre(Sessao sessao) {
        Sessao sessaoAgendada = sessaoRepo.findOneByData(sessao.getData());
        return (sessaoAgendada==null || sessao.getId()==sessaoAgendada.getId());
    }

    @Transactional
    @Override
    public Sessao alterar(UUID id, SessaoDTO dto) {
        Pessoa responsavel = obterResponsavel(dto.getResponsavelId(), String.format("{responsavel.nao.encontrado}", dto.getNomeResponsavel()));

        return sessaoRepo.findById(id)
                .map(sessao->{
                    sessao.setAnotacoes(dto.getAnotacoes());
                    sessao.setMaximoPessoas(dto.getGetMaximoPessoas());
                    sessao.setResponsavel(responsavel);
                    sessao.setTipoSessao(TipoSessao.valueOf(dto.getTipóSessao()));
                    sessao.setData(dto.getData());
                    sessao.setHora(dto.getHora());
                    return sessaoRepo.save(sessao);
                })
                .orElseThrow(()->new RegraNegocioException("{sessao.nao.encontrada}"));
    }

    @Transactional
    @Override
    public void cancelar(UUID id){
        Sessao sessao = sessaoRepo.findById(id)
                .orElseThrow(()->new RegraNegocioException("{sessao.nao.encontrada}"));

        sessao.setStatus(StatusSessao.CANCELADA);
        sessaoRepo.save(sessao);
    }

    @Transactional
    @Override
    public List<ParticipanteSessao> adicionarParticipantes(UUID id, List<Pessoa> pessoas) {
        Sessao sessao = sessaoRepo.findById(id).orElseThrow(()-> new RegraNegocioException("{sessao.nao.encontrada}"));
        List<ParticipanteSessao> participantes = new ArrayList<ParticipanteSessao>();

        pessoas.forEach(pessoa->{
            participantes.add(new ParticipanteSessao(sessao, pessoa));
        });
        
        return participanteSessaoRepo.saveAll(participantes);
    }
    
    @Override
    public void removerParticipante(Pessoa pessoa) {

    }

    @Override
    public void pagar(Sessao sessao, Pessoa pessoa, BigDecimal valorPago, LocalDate dataPagamento) {

    }

    @Override
    public void estornarPagamento(Pessoa pessoa) {

    }

    @Override
    public void confirmarParticipacao(Pessoa pessoa) {

    }

    @Override
    public void desfazerConfirmacaoParticipacao(Pessoa pessoa) {

    }

    @Override
    public void registrarFalta(Sessao sessao, Pessoa pessoa, String observacao) {

    }

    @Override
    public void estornarFalta(Sessao sessao, Pessoa pessoa) {

    }

    @Override
    public void definirPresencaSemAviso(Sessao sessao, Pessoa pessoa, String observacao) {

    }

    @Override
    public void desfazerPresencaSemAviso(Sessao sessao, Pessoa pessoa) {

    }
}
