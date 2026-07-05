package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.EstadoPago;
import com.example.aguacontrol.model.EstadoPedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EstadoPagoRepository extends CrudRepository<EstadoPago, Long> {
    @Query("SELECT e FROM EstadoPago e WHERE UPPER(e.nombre) = 'PENDIENTE'")
    EstadoPago Pendiente();
}
