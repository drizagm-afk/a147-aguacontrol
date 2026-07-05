package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {
    @Query("""
            SELECT p FROM Pedido p
            ORDER BY p.id DESC
            """)
    List<Pedido> browsePedidos();

    @Query("SELECT UPPER(e.nombre) IN :nombres FROM Pedido p JOIN p.estado e WHERE p.id = :id")
    boolean isEstadoIn(Long id, String... nombres);
}
