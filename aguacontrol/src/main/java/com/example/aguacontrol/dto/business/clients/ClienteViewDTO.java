package com.example.aguacontrol.dto.business.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteViewDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private boolean empresa;
    private String telefono;
    private String direccion;
}
