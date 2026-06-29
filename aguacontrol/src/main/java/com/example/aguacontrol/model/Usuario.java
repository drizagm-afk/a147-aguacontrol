package com.example.aguacontrol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "constrasena", nullable = false)
    private String password;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @ManyToMany
    @JoinTable(
            name = "USUARIO_ROLES",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> roles = new HashSet<>();

    @OneToOne(mappedBy = "usuario")
    private Persona persona;
}
