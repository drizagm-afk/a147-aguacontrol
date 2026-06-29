package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Direccion;
import com.example.aguacontrol.model.Persona;
import com.example.aguacontrol.model.Telefono;
import com.example.aguacontrol.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonaRepository extends CrudRepository<Persona, Long> {
    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(:keyword)")
    Iterable<Persona> search(String keyword);

    @Query("""
            SELECT lt
            FROM Telefono lt
            WHERE lt.id = (
                SELECT lp.telefono.id
                FROM Pedido lp
                WHERE lp.persona.id = :id
                AND lp.id = (
                    SELECT MAX(pe.id)
                    FROM Pedido pe
                    WHERE pe.persona.id = :id
                )
            ) OR (
                (SELECT COUNT(*) FROM Pedido pe WHERE pe.persona.id = :id) = 0
                AND lt.id = (
                    SELECT MAX(t.id)
                    FROM Persona p
                        JOIN p.telefonos t
                    WHERE p.id = :id
                )
            )
            """)
    Optional<Telefono> findLastUsedTelefono(Long id);

    @Query("""
            SELECT ld
            FROM Direccion ld
            WHERE ld.id = (
                SELECT lp.direccion.id
                FROM Pedido lp
                WHERE lp.persona.id = :id
                AND lp.id = (
                    SELECT MAX(pe.id)
                    FROM Pedido pe
                    WHERE pe.persona.id = :id
                )
            ) OR (
                (SELECT COUNT(*) FROM Pedido pe WHERE pe.persona.id = :id) = 0
                AND ld.id = (
                    SELECT MAX(d.id)
                    FROM Persona p
                        JOIN p.direcciones d
                    WHERE p.id = :id
                )
            )
            """)
    Optional<Direccion> findLastUsedDireccion(Long id);

    //VALIDATE
    @Query("SELECT COUNT(p) > 0 FROM Persona p " +
            "WHERE LOWER(p.nombre) = TRIM(LOWER(:nombre)) " +
            "AND p.id != :excludeId")
    boolean existsWithNombre(String nombre, Long excludeId);
}
