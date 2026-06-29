package com.example.aguacontrol.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DIRECCIONES")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long id;
    
    @Column(name = "referencia", nullable = false)
    private String referencia;
    
    @Column(name = "latitud", nullable = false)
    private BigDecimal latitud;
    
    @Column(name = "longitud", nullable = false)
    private BigDecimal longitud;
}
