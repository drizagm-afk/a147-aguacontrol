package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Direccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DireccionRepository extends CrudRepository<Direccion, Long> {
    @Query("SELECT d FROM Direccion d WHERE d.referencia = :referencia")
    Optional<Direccion> findByReferencia(String referencia);
}
