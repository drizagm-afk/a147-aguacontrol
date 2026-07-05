package com.example.aguacontrol.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PEDIDOS")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;
    
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;
    
    @Column(name = "fecha_programada")
    private LocalDate fechaProgramada;
    
    @Column(name = "fecha_recepcion")
    private LocalDate fechaRecepcion;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_persona")
    private Persona persona;
    
    @ManyToOne
    @JoinColumn(name = "id_telefono")
    private Telefono telefono;
    
    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;
    
    @ManyToOne
    @JoinColumn(name = "id_estado_pedido")
    private EstadoPedido estado;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_pago")
    private Pago pago;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoPedido> productos;
}
