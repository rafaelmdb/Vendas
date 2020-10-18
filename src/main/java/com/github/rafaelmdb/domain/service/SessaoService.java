package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.api.dto.SessaoDTO;
import com.github.rafaelmdb.domain.entity.ParticipanteSessao;
import com.github.rafaelmdb.domain.entity.Pessoa;
import com.github.rafaelmdb.domain.entity.Sessao;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SessaoService {
    @Transactional
    Sessao criar(SessaoDTO dto);

    @Transactional
    Sessao alterar(UUID id, SessaoDTO dto);

    @Transactional
    void cancelar(UUID id);

    List<ParticipanteSessao> adicionarParticipantes(UUID id, List<Pessoa> pessoa);
    void removerParticipante(Pessoa pessoa);

    void pagar(Sessao sessao, Pessoa pessoa, BigDecimal valorPago, LocalDate dataPagamento);
    void estornarPagamento(Pessoa pessoa);

    void confirmarParticipacao(Pessoa pessoa);
    void desfazerConfirmacaoParticipacao(Pessoa pessoa);

    void registrarFalta(Sessao sessao, Pessoa pessoa, String observacao);
    void estornarFalta(Sessao sessao, Pessoa pessoa);

    void definirPresencaSemAviso(Sessao sessao, Pessoa pessoa, String observacao);
    void desfazerPresencaSemAviso(Sessao sessao, Pessoa pessoa);
}
