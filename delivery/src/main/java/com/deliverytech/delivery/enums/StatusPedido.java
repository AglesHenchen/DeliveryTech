package com.deliverytech.delivery.enums;

public enum StatusPedido {
    RECEBIDO,       // Pedido foi recebido, mas ainda não processado
    EM_PREPARACAO,  // Pedido está sendo preparado
    PRONTO,         // Pedido está pronto para entrega
    EM_ROTA,        // Pedido está a caminho do cliente
    ENTREGUE,       // Pedido foi entregue ao cliente
    CANCELADO       // Pedido foi cancelado
}
