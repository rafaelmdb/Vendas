package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pedido;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import com.github.rafaelmdb.domain.repo.PedidoRepo;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class PedidoServiceImpl extends BaseService implements PedidoService {
    private final PedidoRepo pedidoRepo;

    public PedidoServiceImpl(MessageService messageService, PedidoRepo pedidoRepo){
        super(messageService);
        this.pedidoRepo = pedidoRepo;
    }

    @Override
    public Pedido criar(Pedido pedido) {
        this.validarPedido(pedido);
        return this.pedidoRepo.save(pedido);
    }

    private void validarPedido(Pedido pedido){
        getMessageService().validar(pedido.getCliente()==null, "cliente.nao.informado");
        getMessageService().validar(pedido.getDataEmissao()==null, "data.emissao.nao.informada");
        getMessageService().validar(pedido.getNumero()==null, "numero.nao.inormado");
        getMessageService().validar(pedido.getStatus()==null, "status.invalido");
    }

    @Override
    public Pedido lancar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!= StatusPedido.RASCUNHO, "pedido.deve.estar.em.rascunho.para.lancar");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.LANCADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido aprovar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!= StatusPedido.LANCADO, "pedido.deve.estar.lancado.para.aprovar");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.APROVADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido faturar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!= StatusPedido.APROVADO, "pedido.deve.estar.aprovado.para.faturar");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.FATURADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido cancelar(Pedido pedido) {
        getMessageService().validar(Arrays.asList(StatusPedido.RASCUNHO, StatusPedido.LANCADO).contains(pedido.getStatus()),"pedido.deve.estar.em.rascunho.ou.lancado.para.cancelar");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.CANCELADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reabilitar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.LANCADO,"pedido.deve.estar.lancado.para.reabilitar");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reverterFaturamento(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.FATURADO,"pedido.nao.esta.faturado");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.APROVADO);
        return this.pedidoRepo.save(pedido);    }

    @Override
    public Pedido reverterCancelamento(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.CANCELADO,"pedido.nao.esta.cancelado");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reverterAprovacao(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.APROVADO,"pedido.nao.esta.aprovado");
        this.validarPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }
}
