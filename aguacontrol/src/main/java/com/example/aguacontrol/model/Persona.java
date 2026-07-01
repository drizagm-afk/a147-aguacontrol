package com.example.aguacontrol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PERSONAS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotBlank
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "PERSONA_TELEFONOS",
            joinColumns = @JoinColumn(name = "id_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_telefono")
    )
    @OrderBy("id ASC")
    private List<Telefono> telefonos;

    @ManyToMany
    @JoinTable(
            name = "PERSONA_DIRECCIONES",
            joinColumns = @JoinColumn(name = "id_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_direccion")
    )
    @OrderBy("id ASC")
    private List<Direccion> direcciones;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
