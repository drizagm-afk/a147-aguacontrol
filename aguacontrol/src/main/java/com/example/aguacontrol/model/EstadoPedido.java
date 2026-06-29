package com.example.aguacontrol.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ESTADOS_PEDIDO")
public class EstadoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pedido")
    private Long id;
    
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
}
