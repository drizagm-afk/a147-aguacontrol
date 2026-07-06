package com.example.aguacontrol.dto.business.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoViewDTO {
    private Long id;
    private String codigo;
    private String cliente;
    private boolean empresa;
    private String detalle;
    private BigDecimal total;
    private String estado;

    private boolean canUpdate;
    private boolean canCancel;
}
