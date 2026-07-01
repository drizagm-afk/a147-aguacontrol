package com.example.aguacontrol.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDTO {
    private Long id;
    private String codigo;

    @NotBlank(message = "El Nombre es obligatorio")
    private String nombre;

    //EMPRESA
    private boolean empresa;
    private EmpresaDTO empresaDTO;

    //TELEFONOS
    @Valid
    private List<TelefonoDTO> telefonos = new ArrayList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TelefonoDTO {
        @NotBlank(message = "El Teléfono es obligatorio")
        @Pattern(regexp = "^\\d{9}$", message = "El Teléfono debe tener 9 dígitos")
        private String numero;
    }

    //DIRECCIONES
    @Valid
    private List<DireccionDTO> direcciones = new ArrayList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DireccionDTO {
        @NotBlank(message = "La Dirección es obligatoria")
        private String referencia;
    }
}
