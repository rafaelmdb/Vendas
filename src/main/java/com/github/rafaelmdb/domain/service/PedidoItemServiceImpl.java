package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pedido;
import com.github.rafaelmdb.domain.entity.PedidoItem;
import com.github.rafaelmdb.domain.repo.PedidoItemRepo;
import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class PedidoItemServiceImpl extends BaseService implements PedidoItemService{
    private final PedidoItemRepo pedidoItemRepo;

    public PedidoItemServiceImpl(MessageService messageService, PedidoItemRepo pedidoItemRepo) {
        super(messageService);
        this.pedidoItemRepo = pedidoItemRepo;
    }

    @Override
    public PedidoItem adicionarItem(Pedido pedido, PedidoItem pedidoItem) {
        pedidoItem.setPedido(pedido);
        this.validarPedidoItem(pedidoItem);
        return pedidoItemRepo.save(pedidoItem);
    }

    private void validarPedidoItem(PedidoItem pedidoItem) {
        getMessageService().validar(pedidoItem.getPedido()==null, "pedido.nao.informado");
        getMessageService().validar(pedidoItem.getProdudo()==null, "produto.nao.informado");
        getMessageService().validar(pedidoItem.getDescontoPreco()==null || pedidoItem.getDescontoPreco().compareTo(BigDecimal.ZERO)==-1, "desconto.preco.produto.invalido");
        getMessageService().validar(pedidoItem.getPercDescontoPreco()==null || pedidoItem.getPercDescontoPreco().compareTo(BigDecimal.ZERO)==-1, "perc.desconto.preco.produto.invalido");
        getMessageService().validar(pedidoItem.getPercDescontoTotal()==null || pedidoItem.getPercDescontoTotal().compareTo(BigDecimal.ZERO)==-1, "perc.desconto.total.produto.invalido");
        getMessageService().validar(pedidoItem.getPercIPI()==null || pedidoItem.getPercIPI().compareTo(BigDecimal.ZERO)==-1, "percipi.produto.invalido");
        getMessageService().validar(pedidoItem.getPrecoBruto()==null || pedidoItem.getPrecoBruto().compareTo(BigDecimal.ZERO)==-1, "preco.bruto.produto.invalido");
        getMessageService().validar(pedidoItem.getPrecoLiquido()==null || pedidoItem.getPrecoLiquido().compareTo(BigDecimal.ZERO)==-1, "preco.liquido.produto.invalido");
        getMessageService().validar(pedidoItem.getQuantidade()==null || pedidoItem.getQuantidade().compareTo(BigDecimal.ZERO)==-1, "quantidade.produto.invalida");
        getMessageService().validar(pedidoItem.getValorDescontoTotal()==null || pedidoItem.getValorDescontoTotal().compareTo(BigDecimal.ZERO)==-1, "valor.total.desconto.produto.invalido");
        getMessageService().validar(pedidoItem.getValorIPI()==null || pedidoItem.getValorIPI().compareTo(BigDecimal.ZERO)==-1, "valoripi.produto.invalido");
        getMessageService().validar(pedidoItem.getValorTotalBruto()==null || pedidoItem.getValorTotalBruto().compareTo(BigDecimal.ZERO)==-1, "valor.total.bruto.produto.invalido");
        getMessageService().validar(pedidoItem.getValorTotalLiquido()==null || pedidoItem.getValorTotalLiquido().compareTo(BigDecimal.ZERO)==-1, "valor.total.liquido.produto.invalido");

    }
}
