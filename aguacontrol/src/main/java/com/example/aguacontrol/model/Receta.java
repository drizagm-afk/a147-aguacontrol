package com.example.aguacontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "RECETAS")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receta")
    private Long id;
    
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    
    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonIgnore
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "id_insumo")
    private Insumo insumo;
}