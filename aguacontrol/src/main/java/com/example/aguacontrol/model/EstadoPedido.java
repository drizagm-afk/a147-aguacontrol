package com.example.aguacontrol.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

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

    //ENUM
    public static final String EnEspera = "EN_ESPERA";
    public static final String Programado = "PROGRAMADO";
    public static final String EnRuta = "EN_RUTA";
    public static final String Entregado = "ENTREGADO";
    public static final String Cancelado = "CANCELADO";

    public boolean isIn(String... nombres) {
        return List.of(nombres).contains(nombre);
    }
}
