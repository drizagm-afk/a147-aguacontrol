package com.example.aguacontrol.dto.business.orders;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoUpdateDTO {
    private Long id;
    private Long clienteId;
    private String estado;

    @NotBlank(message = "El Teléfono es obligatorio")
    @Pattern(regexp = "^\\d{9}$", message = "El Teléfono debe tener 9 dígitos")
    private String telefono;
    @NotBlank(message = "La Dirección es obligatoria")
    private String direccion;
}
