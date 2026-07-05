package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.EstadoPedido;
import com.example.aguacontrol.model.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EstadoPedidoRepository extends CrudRepository<EstadoPedido, Long> {
    @Query("SELECT UPPER(e.nombre) IN :nombres FROM EstadoPedido e WHERE e.id = :id")
    boolean isIn(Long id, String... nombres);

    @Query("SELECT UPPER(e.nombre) = 'PROGRAMADO' FROM EstadoPedido e WHERE e.id = :id")
    boolean isProgramado(Long id);

    @Query("SELECT e FROM EstadoPedido e WHERE UPPER(e.nombre) = 'PROGRAMADO'")
    EstadoPedido Programado();

    @Query("SELECT e FROM EstadoPedido e WHERE UPPER(e.nombre) = 'CANCELADO'")
    EstadoPedido Cancelado();
}
