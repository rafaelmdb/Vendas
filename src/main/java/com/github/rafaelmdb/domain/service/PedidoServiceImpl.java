package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pedido;
import com.github.rafaelmdb.domain.entity.PedidoItem;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import com.github.rafaelmdb.domain.repo.PedidoItemRepo;
import com.github.rafaelmdb.domain.repo.PedidoRepo;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PedidoServiceImpl extends BaseService implements PedidoService {
    private final PedidoRepo pedidoRepo;
    private final PedidoItemRepo pedidoItemRepo;

    public PedidoServiceImpl(MessageService messageService, PedidoRepo pedidoRepo, PedidoItemRepo pedidoItemRepo){
        super(messageService);
        this.pedidoRepo = pedidoRepo;
        this.pedidoItemRepo = pedidoItemRepo;
    }

    @Override
    public Pedido criar(Pedido pedido) {
        this.validarDadosCadastraisPedido(pedido);

        getMessageService().validar(pedido.getStatus()!=StatusPedido.RASCUNHO, "status.invalido.para.criar.pedido");

        return this.pedidoRepo.save(pedido);
    }

    private void validarDadosCadastraisPedido(Pedido pedido){
        getMessageService().validar(pedido.getCliente()==null, "cliente.nao.informado");
        getMessageService().validar(pedido.getDataEmissao()==null, "data.emissao.nao.informada");
        getMessageService().validar(pedido.getNumero()==null, "numero.nao.inormado");
        getMessageService().validar(pedido.getStatus()==null, "status.invalido");
    }

    private void totalizarPedido(Pedido pedido){
        List<PedidoItem> itensPedido = pedidoItemRepo.findByPedidoId(pedido.getId());

        pedido.setValorTotalDesconto(itensPedido.stream().map(c->c.getValorDescontoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
        pedido.setValorTotalLiquido(itensPedido.stream().map(c->c.getValorTotalLiquido()).reduce(BigDecimal.ZERO, BigDecimal::add));
        pedido.setValorTotalIPI(itensPedido.stream().map(c->c.getValorIPI()).reduce(BigDecimal.ZERO, BigDecimal::add));
        pedido.setValorTotalBruto(itensPedido.stream().map(c->c.getValorTotalBruto()).reduce(BigDecimal.ZERO, BigDecimal::add));

        pedido.setValorTotalPedido(
                pedido.getValorTotalLiquido()
                        .add(pedido.getValorTotalIPI())
        );
    }

    @Override
    public Pedido lancar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!= StatusPedido.RASCUNHO, "pedido.deve.estar.em.rascunho.para.lancar");
        this.validarDadosCadastraisPedido(pedido);

        this.totalizarPedido(pedido);

        this.validarLancamentoPedido(pedido);
        pedido.setStatus(StatusPedido.LANCADO);
        return this.pedidoRepo.save(pedido);
    }

    private void validarLancamentoPedido(Pedido pedido) {
        getMessageService().validar(pedidoItemRepo.findByPedidoId(pedido.getId()).stream().count()==0, "pedido.sem.itens");

        BigDecimal soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c->c.getValorTotalBruto()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalBruto()), "total.bruto.pedido.diverge.soma.itens");

        soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c->c.getValorTotalLiquido()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalLiquido()), "total.liquido.pedido.diverge.soma.itens");

        soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c->c.getValorIPI()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalIPI()), "total.ipi.pedido.diverge.soma.itens");

        soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c->c.getValorDescontoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalDesconto()), "total.desconto.pedido.diverge.soma.itens");

        getMessageService().validar(!pedido.getValorTotalLiquido().add(pedido.getValorTotalIPI()).equals(pedido.getValorTotalPedido()),
            "total.pedido.diverge.soma.totalizadores");
    }

    @Override
    public Pedido aprovar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!= StatusPedido.LANCADO, "pedido.deve.estar.lancado.para.aprovar");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.APROVADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido faturar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!= StatusPedido.APROVADO, "pedido.deve.estar.aprovado.para.faturar");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.FATURADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido cancelar(Pedido pedido) {
        getMessageService().validar(!Arrays.asList(StatusPedido.RASCUNHO, StatusPedido.LANCADO, StatusPedido.APROVADO).contains(pedido.getStatus()),"status.invalido.para.cancelar.pedido");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.CANCELADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reabilitar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.LANCADO,"pedido.deve.estar.lancado.para.reabilitar");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reverterFaturamento(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.FATURADO,"pedido.nao.esta.faturado");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.APROVADO);
        return this.pedidoRepo.save(pedido);    }

    @Override
    public Pedido reverterCancelamento(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.CANCELADO,"pedido.nao.esta.cancelado");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reverterAprovacao(Pedido pedido) {
        getMessageService().validar(pedido.getStatus()!=StatusPedido.APROVADO,"pedido.nao.esta.aprovado");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }
}
