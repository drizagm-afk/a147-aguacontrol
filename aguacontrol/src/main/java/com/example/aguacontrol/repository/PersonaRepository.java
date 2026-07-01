package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Direccion;
import com.example.aguacontrol.model.Persona;
import com.example.aguacontrol.model.Telefono;
import com.example.aguacontrol.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface PersonaRepository extends CrudRepository<Persona, Long> {
    @Query("""
            SELECT p FROM Persona p
            WHERE :keyword IS NULL
            OR LOWER(p.nombre) LIKE LOWER(TRIM(:keyword)) 
            ORDER BY p.id DESC
            """)
    List<Persona> browse(String keyword);

    //TELEFONOS
    @Query("""
            SELECT p.id AS personaId, lt.id AS telefonoId
            FROM Persona p JOIN p.telefonos lt
            WHERE p.id IN :ids
            AND (lt.id = (
                SELECT lp.telefono.id
                FROM Pedido lp
                WHERE lp.persona.id = p.id
                AND lp.fechaSolicitud = (
                    SELECT MAX(pe.fechaSolicitud)
                    FROM Pedido pe JOIN pe.persona pep JOIN pep.telefonos t
                    WHERE pe.telefono.id = t.id AND pep.id = p.id
                )
            ) OR (
                0 = (
                    SELECT COUNT(*)
                    FROM Pedido pe JOIN pe.persona pep JOIN pep.telefonos t
                    WHERE pe.telefono.id = t.id AND pep.id = p.id
                ) AND lt.id = (
                    SELECT MAX(t.id)
                    FROM Persona pep JOIN pep.telefonos t
                    WHERE pep.id = p.id
                )
            ))
            """)
    List<PersonaTelefonoProy> findLastUsedTelefonoRaw(List<Long> ids);

    interface PersonaTelefonoProy {
        Long getPersonaId();

        Long getTelefonoId();
    }

    default Long findLastUsedTelefonoId(Long id) {
        var DTOs = findLastUsedTelefonoRaw(List.of(id));
        if (DTOs.isEmpty())
            return null;

        return DTOs.getFirst().getTelefonoId();
    }

    default Map<Long, Long> findLastUsedTelefonoIds(List<Long> ids) {
        return findLastUsedTelefonoRaw(ids).stream()
                .collect(Collectors.toMap(
                        PersonaTelefonoProy::getPersonaId,
                        PersonaTelefonoProy::getTelefonoId
                ));
    }

    //DIRECCIONES
    @Query("""
            SELECT p.id AS personaId, ld.id AS direccionId
            FROM Persona p JOIN p.direcciones ld
            WHERE p.id IN :ids
            AND (ld.id = (
                SELECT lp.direccion.id
                FROM Pedido lp
                WHERE lp.persona.id = p.id
                AND lp.fechaSolicitud = (
                    SELECT MAX(pe.fechaSolicitud)
                    FROM Pedido pe JOIN pe.persona pep JOIN pep.direcciones d
                    WHERE pe.direccion.id = d.id AND pep.id = p.id
                )
            ) OR (
                0 = (
                    SELECT COUNT(*)
                    FROM Pedido pe JOIN pe.persona pep JOIN pep.direcciones d
                    WHERE pe.direccion.id = d.id AND pep.id = p.id
                ) AND ld.id = (
                    SELECT MAX(d.id)
                    FROM Persona pep JOIN pep.direcciones d
                    WHERE pep.id = p.id
                )
            ))
            """)
    List<PersonaDireccionProy> findLastUsedDireccionRaw(List<Long> ids);

    interface PersonaDireccionProy {
        Long getPersonaId();

        Long getDireccionId();
    }

    default Long findLastUsedDireccionId(Long id) {
        var DTOs = findLastUsedDireccionRaw(List.of(id));
        if (DTOs.isEmpty())
            return null;

        return DTOs.getFirst().getDireccionId();
    }

    default Map<Long, Long> findLastUsedDireccionIds(List<Long> ids) {
        return findLastUsedDireccionRaw(ids).stream()
                .collect(Collectors.toMap(
                        PersonaDireccionProy::getPersonaId,
                        PersonaDireccionProy::getDireccionId
                ));
    }

    //EXISTS
    @Query("""
            SELECT COUNT(p) > 0 FROM Persona p
            WHERE LOWER(p.nombre) = TRIM(LOWER(:nombre)) AND p.id != :excludeId
            """)
    boolean existsWithNombre(String nombre, Long excludeId);
}
