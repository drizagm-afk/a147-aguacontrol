package com.example.aguacontrol.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "EMPRESAS")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Empresa extends Persona {
    @Column(name = "ruc", nullable = false, unique = true)
    private String ruc;
}
