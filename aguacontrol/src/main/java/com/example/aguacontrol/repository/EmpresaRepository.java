package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Empresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
    @Query("SELECT e FROM Empresa e WHERE e.ruc = :ruc")
    Optional<Empresa> findByRuc(String ruc);

    //VALIDATE
    @Query("SELECT COUNT(e) > 0 FROM Empresa e " +
            "WHERE e.ruc = :ruc " +
            "AND e.id != :excludeId")
    boolean existsWithRuc(String ruc, Long excludeId);
}
