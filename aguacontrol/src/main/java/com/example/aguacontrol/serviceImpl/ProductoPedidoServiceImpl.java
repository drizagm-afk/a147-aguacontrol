package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.ProductoPedido;
import com.example.aguacontrol.repository.ProductoPedidoRepository;
import com.example.aguacontrol.service.ProductoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoPedidoServiceImpl implements ProductoPedidoService {
    private final ProductoPedidoRepository repo;

    @Override
    public ProductoPedido create(ProductoPedido productoPedido) {
        return repo.save(productoPedido);
    }

    @Override
    public ProductoPedido update(ProductoPedido productoPedido) {
        return repo.save(productoPedido);
    }

    @Override
    public Optional<ProductoPedido> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<ProductoPedido> readAll() {
        return repo.findAll();
    }
}
