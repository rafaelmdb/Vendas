package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.domain.entity.Pedido;
import com.github.rafaelmdb.domain.entity.PedidoItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public interface PedidoItemService {
    PedidoItem adicionarItem(Pedido pedido, PedidoItem pedidoItem);
}
