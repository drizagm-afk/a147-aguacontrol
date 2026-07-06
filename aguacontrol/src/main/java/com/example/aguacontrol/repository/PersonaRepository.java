package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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

    @Query("""
            SELECT p FROM Persona p
            WHERE LOWER(p.nombre) = TRIM(LOWER(:nombre))
            """)
    Optional<Persona> findByNombre(String nombre);

    //TELEFONOS
    @Query("""
            SELECT p.id AS personaId, lt.id AS telefonoId
            FROM Persona p JOIN p.telefonos lt
            WHERE p.id IN :ids
            AND lt.id = COALESCE((
                SELECT lpe.telefono.id
                FROM Pedido lpe
                WHERE lpe.id = (
                    SELECT MAX(pe.id)
                    FROM Pedido pe
                    WHERE pe.persona = p AND pe.telefono MEMBER OF p.telefonos
                )),(
                SELECT MAX(t.id)
                FROM p.telefonos t
            ))
            """)
    List<PersonaTelefonoProy> findLastUsedTelefonoRaw(List<Long> ids);

    interface PersonaTelefonoProy {
        Long getPersonaId();

        Long getTelefonoId();
    }

    default Long findLastUsedTelefonoId(Long id) {
        var raw = findLastUsedTelefonoRaw(List.of(id));
        if (raw.isEmpty())
            return null;

        return raw.getFirst().getTelefonoId();
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
            AND ld.id = COALESCE((
                SELECT lpe.direccion.id
                FROM Pedido lpe
                WHERE lpe.id = (
                    SELECT MAX(pe.id)
                    FROM Pedido pe
                    WHERE pe.persona = p AND pe.direccion MEMBER OF p.direcciones
                )),(
                SELECT MAX(d.id)
                FROM p.direcciones d
            ))
            """)
    List<PersonaDireccionProy> findLastUsedDireccionRaw(List<Long> ids);

    interface PersonaDireccionProy {
        Long getPersonaId();

        Long getDireccionId();
    }

    default Long findLastUsedDireccionId(Long id) {
        var raw = findLastUsedDireccionRaw(List.of(id));
        if (raw.isEmpty())
            return null;

        return raw.getFirst().getDireccionId();
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
