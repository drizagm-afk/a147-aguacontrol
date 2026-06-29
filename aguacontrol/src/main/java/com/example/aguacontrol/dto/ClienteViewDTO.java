package com.example.aguacontrol.dto;

import com.example.aguacontrol.model.Direccion;
import com.example.aguacontrol.model.Telefono;
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
    private Telefono telefono;
    private Direccion direccion;
}