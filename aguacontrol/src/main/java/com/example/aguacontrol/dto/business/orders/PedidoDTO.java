package com.example.aguacontrol.dto.business.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoDTO {
    @NotBlank(message = "El Nombre del Cliente es obligatorio")
    private String cliente;

    @NotBlank(message = "El Teléfono es obligatorio")
    @Pattern(regexp = "^\\d{9}$", message = "El Teléfono debe tener 9 dígitos")
    private String telefono;
    @NotBlank(message = "La Dirección es obligatoria")
    private String direccion;

    //PRODUCTOS
    @Valid
    private List<ProductoDTO> productos = new ArrayList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ProductoDTO {
        @NotNull(message = "El Producto debe ser válido")
        private Long id;
        @NotNull(message="La Cantidad es obligatoria")
        @DecimalMin(value = "0", message = "La Cantidad debe ser mayor a 0", inclusive = false)
        private int cantidad;
    }

    @NotNull(message = "El Método de Pago es obligatorio")
    private Long metodoPagoId;
}
