package com.example.aguacontrol.repository;

import com.example.aguacontrol.model.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductoRepository extends CrudRepository<Producto, Long> {
    @Query(
            value = "SELECT id_producto as id, stock FROM V_M_STOCK_PRODUCTOS WHERE id_producto=:id",
            nativeQuery = true
    )
    Optional<ProductoStockProy> findStockRaw(Long id);

    interface ProductoStockProy {
        Long getId();
        int getStock();
    }

    default int findStock(Long id) {
        return findStockRaw(id)
                .map(ProductoStockProy::getStock)
                .orElse(-1);
    }
}
