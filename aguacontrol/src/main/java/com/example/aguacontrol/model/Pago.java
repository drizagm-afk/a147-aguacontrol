package com.example.aguacontrol.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PAGOS")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;
    
    @Column(name = "fecha_pago")
    private LocalDate fechaPago;
    
    @Column(name = "monto")
    private BigDecimal monto;
    
    @Column(name = "foto_pago")
    private String fotoPago;
    
    @ManyToOne
    @JoinColumn(name = "id_estado_pago")
    private EstadoPago estado;
    
    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodo;
}
