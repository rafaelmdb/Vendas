package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pedido;
import com.github.rafaelmdb.domain.entity.PedidoItem;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import com.github.rafaelmdb.domain.repo.PedidoItemRepo;
import com.github.rafaelmdb.domain.repo.PedidoRepo;
import com.github.rafaelmdb.exception.RegraNegocioException;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PedidoServiceImpl extends BaseService implements PedidoService {
    private final PedidoRepo pedidoRepo;
    private final PedidoItemRepo pedidoItemRepo;

    public PedidoServiceImpl(MessageService messageService, PedidoRepo pedidoRepo, PedidoItemRepo pedidoItemRepo) {
        super(messageService);
        this.pedidoRepo = pedidoRepo;
        this.pedidoItemRepo = pedidoItemRepo;
    }

    @Override
    public Pedido criar(Pedido pedido) {
        this.validarDadosCadastraisPedido(pedido);

        getMessageService().validar(pedido.getStatus() != StatusPedido.RASCUNHO, "status.invalido.para.criar.pedido");

        return this.pedidoRepo.save(pedido);
    }

    private void validarDadosCadastraisPedido(Pedido pedido) {
        getMessageService().validar(pedido.getCliente() == null, "cliente.nao.informado");
        getMessageService().validar(pedido.getDataEmissao() == null, "data.emissao.nao.informada");
        getMessageService().validar(pedido.getNumero() == null, "numero.nao.inormado");
        getMessageService().validar(pedido.getStatus() == null, "status.invalido");
    }

    @Override
    public Pedido lancar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus() != StatusPedido.RASCUNHO, "pedido.deve.estar.em.rascunho.para.lancar");
        this.validarDadosCadastraisPedido(pedido);

        this.totalizarPedido(pedido);

        this.validarLancamentoPedido(pedido);
        pedido.setStatus(StatusPedido.LANCADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido aprovar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus() != StatusPedido.LANCADO, "pedido.deve.estar.lancado.para.aprovar");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.APROVADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido faturar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus() != StatusPedido.APROVADO, "pedido.deve.estar.aprovado.para.faturar");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.FATURADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido cancelar(Pedido pedido) {
        getMessageService().validar(!Arrays.asList(StatusPedido.RASCUNHO, StatusPedido.LANCADO, StatusPedido.APROVADO).contains(pedido.getStatus()), "status.invalido.para.cancelar.pedido");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.CANCELADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reabilitar(Pedido pedido) {
        getMessageService().validar(pedido.getStatus() != StatusPedido.LANCADO, "pedido.deve.estar.lancado.para.reabilitar");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reverterFaturamento(Pedido pedido) {
        getMessageService().validar(pedido.getStatus() != StatusPedido.FATURADO, "pedido.nao.esta.faturado");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.APROVADO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reverterCancelamento(Pedido pedido) {
        getMessageService().validar(pedido.getStatus() != StatusPedido.CANCELADO, "pedido.nao.esta.cancelado");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido reverterAprovacao(Pedido pedido) {
        getMessageService().validar(pedido.getStatus() != StatusPedido.APROVADO, "pedido.nao.esta.aprovado");
        this.validarDadosCadastraisPedido(pedido);
        pedido.setStatus(StatusPedido.RASCUNHO);
        return this.pedidoRepo.save(pedido);
    }

    @Override
    public Pedido obterPorId(UUID id) {
        return this.pedidoRepo.findById(id)
                .orElseThrow(() -> new RegraNegocioException(getMessageService().getMessage("pedido.nao.encontrado", null)));
    }


    @Override
    public PedidoItem adicionarItem(Pedido pedido, PedidoItem pedidoItem) {
        pedidoItem.setPedido(pedido);
        this.validarPedidoItem(pedidoItem);
        return pedidoItemRepo.save(pedidoItem);
    }

    @Override
    public PedidoItem obterItemPorId(UUID id) {
        return this.pedidoItemRepo.findById(id)
                .orElseThrow(() -> new RegraNegocioException(getMessageService().getMessage("item.do.pedido.nao.encontrado", null)));
    }

    @Override
    public List<PedidoItem> obterItensPorPedido(UUID id) {
        return this.pedidoItemRepo.findByPedidoId(id);
    }

    @Override
    public void removerItem(UUID id) {
        PedidoItem pedidoItem = this.obterItemPorId(id);
        getMessageService().validar(pedidoItem.getPedido().getStatus() != StatusPedido.RASCUNHO, "status.invalido.para.remover.item.pedido");

        pedidoItemRepo.delete(pedidoItem);
    }

    @Override
    public PedidoItem alterarItem(PedidoItem pedidoItem) {
        this.validarAlteracaoItemPedido(pedidoItem);
        this.pedidoItemRepo.save(pedidoItem);

        return pedidoItem;
    }

    private void validarAlteracaoItemPedido(PedidoItem pedidoItem) {
        getMessageService().validar(pedidoItem.getPedido().getStatus()!=StatusPedido.RASCUNHO, "status.invalido.para.alterar.pedido");
        PedidoItem original = this.obterItemPorId(pedidoItem.getId());
        getMessageService().validar(!original.getPedido().equals(pedidoItem.getPedido()), "item.do.pedido.nao.encontrado");
    }

    private void validarPedidoItem(PedidoItem pedidoItem) {
        getMessageService().validar(pedidoItem.getPedido() == null, "pedido.nao.informado");
        getMessageService().validar(pedidoItem.getProdudo() == null, "produto.nao.informado");
        getMessageService().validar(pedidoItem.getDescontoPreco() == null || pedidoItem.getDescontoPreco().compareTo(BigDecimal.ZERO) == -1, "desconto.preco.produto.invalido");
        getMessageService().validar(pedidoItem.getPercDescontoPreco() == null || pedidoItem.getPercDescontoPreco().compareTo(BigDecimal.ZERO) == -1, "perc.desconto.preco.produto.invalido");
        getMessageService().validar(pedidoItem.getPercDescontoTotal() == null || pedidoItem.getPercDescontoTotal().compareTo(BigDecimal.ZERO) == -1, "perc.desconto.total.produto.invalido");
        getMessageService().validar(pedidoItem.getPercIPI() == null || pedidoItem.getPercIPI().compareTo(BigDecimal.ZERO) == -1, "percipi.produto.invalido");
        getMessageService().validar(pedidoItem.getPrecoBruto() == null || pedidoItem.getPrecoBruto().compareTo(BigDecimal.ZERO) == -1, "preco.bruto.produto.invalido");
        getMessageService().validar(pedidoItem.getPrecoLiquido() == null || pedidoItem.getPrecoLiquido().compareTo(BigDecimal.ZERO) == -1, "preco.liquido.produto.invalido");
        getMessageService().validar(pedidoItem.getQuantidade() == null || pedidoItem.getQuantidade().compareTo(BigDecimal.ZERO) == -1, "quantidade.produto.invalida");
        getMessageService().validar(pedidoItem.getValorDescontoTotal() == null || pedidoItem.getValorDescontoTotal().compareTo(BigDecimal.ZERO) == -1, "valor.total.desconto.produto.invalido");
        getMessageService().validar(pedidoItem.getValorIPI() == null || pedidoItem.getValorIPI().compareTo(BigDecimal.ZERO) == -1, "valoripi.produto.invalido");
        getMessageService().validar(pedidoItem.getValorTotalBruto() == null || pedidoItem.getValorTotalBruto().compareTo(BigDecimal.ZERO) == -1, "valor.total.bruto.produto.invalido");
        getMessageService().validar(pedidoItem.getValorTotalLiquido() == null || pedidoItem.getValorTotalLiquido().compareTo(BigDecimal.ZERO) == -1, "valor.total.liquido.produto.invalido");
    }

    private void validarLancamentoPedido(Pedido pedido) {
        getMessageService().validar(pedidoItemRepo.findByPedidoId(pedido.getId()).stream().count() == 0, "pedido.sem.itens");

        BigDecimal soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c -> c.getValorTotalBruto()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalBruto()), "total.bruto.pedido.diverge.soma.itens");

        soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c -> c.getValorTotalLiquido()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalLiquido()), "total.liquido.pedido.diverge.soma.itens");

        soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c -> c.getValorIPI()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalIPI()), "total.ipi.pedido.diverge.soma.itens");

        soma = pedidoItemRepo.findByPedidoId(pedido.getId()).stream().map(c -> c.getValorDescontoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        getMessageService().validar(!soma.equals(pedido.getValorTotalDesconto()), "total.desconto.pedido.diverge.soma.itens");

        getMessageService().validar(!pedido.getValorTotalLiquido().add(pedido.getValorTotalIPI()).equals(pedido.getValorTotalPedido()),
                "total.pedido.diverge.soma.totalizadores");
    }

    private void totalizarPedido(Pedido pedido) {
        List<PedidoItem> itensPedido = pedidoItemRepo.findByPedidoId(pedido.getId());

        pedido.setValorTotalDesconto(itensPedido.stream().map(c -> c.getValorDescontoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
        pedido.setValorTotalLiquido(itensPedido.stream().map(c -> c.getValorTotalLiquido()).reduce(BigDecimal.ZERO, BigDecimal::add));
        pedido.setValorTotalIPI(itensPedido.stream().map(c -> c.getValorIPI()).reduce(BigDecimal.ZERO, BigDecimal::add));
        pedido.setValorTotalBruto(itensPedido.stream().map(c -> c.getValorTotalBruto()).reduce(BigDecimal.ZERO, BigDecimal::add));

        pedido.setValorTotalPedido(
                pedido.getValorTotalLiquido()
                        .add(pedido.getValorTotalIPI())
        );
    }
}