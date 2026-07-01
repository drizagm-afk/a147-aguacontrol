package com.example.aguacontrol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpFormDTO {
    @NotBlank(message = "El Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El Email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    @NotBlank(message = "La Contraseña es obligatoria")
    private String password;

    //EMPRESA
    private boolean empresa;
    private EmpresaDTO empresaDTO;
}

