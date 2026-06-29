package com.example.aguacontrol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteRegistryDTO {
    private Long id;

    @NotBlank(message = "El Nombre es obligatorio")
    private String nombre;

    //EMPRESA
    private boolean empresa;
    private EmpresaRegistryDTO empresaForm = new EmpresaRegistryDTO();
}

