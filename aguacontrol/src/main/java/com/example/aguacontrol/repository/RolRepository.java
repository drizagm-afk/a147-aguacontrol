package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RolRepository extends CrudRepository<Rol, Long> {
    @Query("SELECT r FROM Rol r WHERE UPPER(r.nombre) = 'CLIENTE'")
    Rol Cliente();
}
