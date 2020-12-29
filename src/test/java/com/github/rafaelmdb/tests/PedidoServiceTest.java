package com.github.rafaelmdb.tests;

import com.github.rafaelmdb.domain.entity.Cliente;
import com.github.rafaelmdb.domain.entity.Pedido;
import com.github.rafaelmdb.domain.enums.StatusPedido;
import com.github.rafaelmdb.domain.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PedidoServiceTest {
    @Autowired
    private PedidoService pedidoService;

    private Pedido criarPedido(){
        Cliente cliente = new Cliente();
        cliente.setId(UUID.fromString("07831cae-9423-443b-ae93-cd5e65a8b69c"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataEmissao(LocalDate.now());
        pedido.setNumero("1");
        pedido.setValorTotalBruto(10);
        pedido.setValorTotalDesconto(0);
        pedido.setValortotalIPI(0);
        pedido.setValorTotalPedido(10);
        pedido.setValortotalProduto(0);
        return pedidoService.criar(pedido);
    }

    @Test
    public void criarPedidoSucesso(){
        Pedido pedido;

        try {
            pedido = criarPedido();
            assertEquals(pedido.getDataEmissao(), LocalDate.now(), "Data divergente");
            assertEquals(pedido.getStatus(), StatusPedido.RASCUNHO, "Status diferente de criado");
        }
        catch(Exception e){
            assertNull(e==null, e.getMessage());
        }
    }

    @Test
    public void lancarPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.lancar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.LANCADO, "Status diferente de lançado");
    }

    @Test
    public void aprovarPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.APROVADO, "Status diferente de aprovado");
    }

    @Test
    public void reverterAprovacaoPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        pedidoService.reverterAprovacao(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.RASCUNHO, "Status diferente de lançado");
    }

    @Test
    public void cancelarPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedidoService.cancelar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.CANCELADO, "Status diferente de cancelado");

        pedido = criarPedido();
        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        pedidoService.cancelar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.CANCELADO, "Status diferente de cancelado");
    }

    @Test
    public void reverterCancelamentoPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.cancelar(pedido);
        pedidoService.reverterCancelamento(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.LANCADO, "Status diferente de lançado");
    }

    @Test
    public void faturarPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.lancar(pedido);
        pedidoService.aprovar(pedido);
        pedidoService.faturar(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.FATURADO, "Status diferente de faturado");
    }

    @Test
    public void reverterFaturamentoPedidoSucesso(){
        Pedido pedido = criarPedido();
        pedido = pedidoService.lancar(pedido);
        pedido = pedidoService.aprovar(pedido);
        pedido = pedidoService.faturar(pedido);
        pedidoService.reverterFaturamento(pedido);
        assertEquals(pedido.getStatus(), StatusPedido.APROVADO, "Status diferente de aprovado");
    }
}
