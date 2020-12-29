package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public interface PedidoService {
    @Transactional
    Pedido criar(Pedido pedido);

    @Transactional
    Pedido lancar(Pedido pedido);

    @Transactional
    Pedido aprovar(Pedido pedido);

    @Transactional
    Pedido faturar(Pedido pedido);

    @Transactional
    Pedido cancelar(Pedido pedido);

    @Transactional
    Pedido reabilitar(Pedido pedido);

    @Transactional
    Pedido reverterFaturamento(Pedido pedido);

    @Transactional
    Pedido reverterCancelamento(Pedido pedido);

    @Transactional
    Pedido reverterAprovacao(Pedido pedido);
}
