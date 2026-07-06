package com.example.aguacontrol.service;

import com.example.aguacontrol.dto.business.orders.PedidoDTO;
import com.example.aguacontrol.dto.business.orders.PedidoUpdateDTO;
import com.example.aguacontrol.dto.business.orders.PedidoViewDTO;
import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Pedido;
import com.example.aguacontrol.utils.validation.ValidationErrors;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PedidoService extends CrudService<Pedido, Long> {
    //PEDIDO VIEW DTO
    @Transactional(readOnly = true)
    List<PedidoViewDTO> browsePedidos(String keyword);

    //PEDIDO DTO
    ValidationErrors validatePedido(PedidoDTO dto);

    @Transactional
    Pedido createPedido(PedidoDTO dto);

    //PEDIDO UPDATE DTO
    @Transactional(readOnly = true)
    Optional<PedidoUpdateDTO> readPedidoUpdate(Long id);

    ValidationErrors validatePedidoUpdate(PedidoUpdateDTO dto);

    @Transactional
    Pedido updatePedido(PedidoUpdateDTO dto);

    //CANCEL PEDIDO
    @Transactional
    void cancelPedido(Long id);
}
