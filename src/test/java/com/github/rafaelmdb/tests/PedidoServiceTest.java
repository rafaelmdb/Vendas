package com.github.rafaelmdb.tests;

import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.domain.entity.Pedido;
import com.github.rafaelmdb.domain.entity.PedidoItem;
import com.github.rafaelmdb.domain.entity.Produto;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import com.github.rafaelmdb.domain.service.PedidoService;
import com.github.rafaelmdb.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PedidoServiceTest {
    @Autowired
    private PedidoService pedidoService;

    private Pedido criarPedido() {
        Cliente cliente = new Cliente();
        cliente.setId(UUID.fromString("07831cae-9423-443b-ae93-cd5e65a8b69c"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataEmissao(LocalDate.now());
        pedido.setNumero("1");
        pedido.setValorTotalBruto(BigDecimal.valueOf(5.85));
        pedido.setValorTotalDesconto(BigDecimal.valueOf(0.05));
        pedido.setValorTotalIPI(BigDecimal.valueOf(0.12));
        pedido.setValorTotalLiquido(BigDecimal.valueOf(5.80));
        pedido.setValorTotalPedido(BigDecimal.valueOf(5.92));
        return pedido;
    }

    private PedidoItem criarItem(Pedido pedido) {
        Produto produto = new Produto();
        produto.setId(UUID.fromString("07831cae-9423-443b-ae93-cd5e65a8b69c"));

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setQuantidade(BigDecimal.valueOf(2));
        pedidoItem.setPrecoBruto(BigDecimal.valueOf(3));
        pedidoItem.setDescontoPreco(BigDecimal.valueOf(0.075));
        pedidoItem.setPercDescontoPreco(BigDecimal.valueOf(2.5));
        pedidoItem.setPrecoLiquido(BigDecimal.valueOf(2.925));
        pedidoItem.setProdudo(produto);
        pedidoItem.setPercIPI(BigDecimal.valueOf(2));
        pedidoItem.setPercDescontoTotal(BigDecimal.valueOf(0.8547));
        pedidoItem.setValorDescontoTotal(BigDecimal.valueOf(0.05));
        pedidoItem.setValorIPI(BigDecimal.valueOf(0.12));
        pedidoItem.setValorTotalBruto(BigDecimal.valueOf(5.85));
        pedidoItem.setValorTotalLiquido(BigDecimal.valueOf(5.8));

        return pedidoItem;
    }

    @Test
    public void criarPedidoSucesso() {
        Pedido pedido;

        try {
            pedido = criarPedido();
            pedido = pedidoService.criar(pedido);
            assertEquals(pedido.getDataEmissao(), LocalDate.now(), "Data divergente");
            assertEquals(pedido.getStatus(), StatusPedido.RASCUNHO, "Status diferente de criado");
        } catch (Exception e) {
            assertNull(e == null, e.getMessage());
        }
    }

    @Test
    public void lancarPedidoSucesso() {
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.LANCADO, "Status diferente de lançado");
    }

    @Test
    public void criarPedidoSemClienteFalha() {
        Pedido pedido = criarPedido();
        pedido.setCliente(null);
        Pedido finalPedido = pedido;
        assertThrows(RegraNegocioException.class, () -> {
            pedidoService.criar(finalPedido);
        });
    }

    @Test
    public void criarPedidoStatusInvalidoFalha() {
        Pedido pedido = criarPedido();
        pedido.setStatus(StatusPedido.APROVADO);
        Pedido finalPedido = pedido;
        assertThrows(RegraNegocioException.class, () -> {
            pedidoService.criar(finalPedido);
        });
    }

    @Test
    public void lancarPedidoSemItensFalha(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);
        Pedido finalPedido = pedido;
        assertThrows(RegraNegocioException.class, () -> {pedidoService.lancar(finalPedido);});
    }

    @Test
    public void aprovarPedidoSucesso() {
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.APROVADO, "Status diferente de aprovado");
    }

    @Test
    public void reverterAprovacaoPedidoSucesso() {
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        pedidoService.reverterAprovacao(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.RASCUNHO, "Status diferente de lançado");
    }

    @Test
    public void cancelarPedidoSucesso() {
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);
        pedidoService.cancelar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.CANCELADO, "Status diferente de cancelado");

        pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        pedidoService.cancelar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.CANCELADO, "Status diferente de cancelado");

        pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        pedidoService.cancelar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.CANCELADO, "Status diferente de cancelado");
    }

    @Test
    public void reverterCancelamentoPedidoSucesso() {
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.cancelar(pedido);
        pedidoService.reverterCancelamento(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.RASCUNHO, "Status diferente de rascunho");
    }

    @Test
    public void faturarPedidoSucesso() {
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        pedidoService.aprovar(pedido);
        pedidoService.faturar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.FATURADO, "Status diferente de faturado");
    }

    @Test
    public void reverterFaturamentoPedidoSucesso() {
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        pedido = pedidoService.faturar(pedido);
        pedidoService.reverterFaturamento(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.APROVADO, "Status diferente de aprovado");
    }

    @Test
    public void removerItemPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        assertEquals(pedidoService.obterItensPorPedido(pedido.getId()).stream().count(), 2, "Quantidade de itens é divergente");
        pedidoService.removerItem(pedidoItem.getId());
        assertEquals(pedidoService.obterItensPorPedido(pedido.getId()).stream().count(), 1, "Quantidade de itens é divergente");
    }

    @Test
    public void removerItemPedidoDiferenteRascunhoFalha(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedido = pedidoService.lancar(pedido);

        PedidoItem finalPedidoItem = pedidoItem;
        assertThrows(RegraNegocioException.class, ()->pedidoService.removerItem(finalPedidoItem.getId()));
    }

    @Test
    public void alterarItemPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.criar(pedido);

        PedidoItem pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedidoItem = this.criarItem(pedido);
        pedidoItem = pedidoService.adicionarItem(pedido, pedidoItem);

        pedidoItem.setQuantidade(BigDecimal.valueOf(1));
        pedidoItem.setPrecoBruto(BigDecimal.valueOf(1));

        pedidoItem = pedidoService.alterarItem(pedidoItem);

        assertEquals(pedidoItem.getQuantidade(), BigDecimal.valueOf(1));
        assertEquals(pedidoItem.getPrecoBruto(), BigDecimal.valueOf(1));
    }
}
