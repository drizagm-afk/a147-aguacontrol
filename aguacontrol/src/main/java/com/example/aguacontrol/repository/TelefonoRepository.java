package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Telefono;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TelefonoRepository extends CrudRepository<Telefono, Long> {
    @Query("SELECT t FROM Telefono t WHERE t.numero = :numero")
    Optional<Telefono> findByNumero(String numero);
}
