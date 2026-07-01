package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Query("SELECT u FROM Persona p JOIN p.usuario u WHERE LOWER(p.nombre) = TRIM(LOWER(:nombre))")
    Optional<Usuario> findByNombre(String nombre);

    @Query("SELECT u FROM Usuario u WHERE u.email = TRIM(LOWER(:email))")
    Optional<Usuario> findByEmail(String email);

    @Query("""
            SELECT distinct u FROM Persona p JOIN p.usuario u
            LEFT JOIN fetch u.roles r
            LEFT JOIN fetch r.permisos pe
            WHERE LOWER(p.nombre) = TRIM(LOWER(:identifier)) OR u.email = TRIM(LOWER(:identifier))
            """)
    Optional<Usuario> findByNombreOrEmailEager(String identifier);

    //EXISTS
    @Query("""
            SELECT COUNT(u) > 0 FROM Usuario u
            WHERE u.email = TRIM(LOWER(:email)) AND u.id != :excludeId
            """)
    boolean existsWithEmail(String email, Long excludeId);
}
