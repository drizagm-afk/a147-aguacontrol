package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Producto;
import com.example.aguacontrol.repository.ProductoRepository;
import com.example.aguacontrol.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository repo;

    @Override
    public Producto create(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public Producto update(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public Optional<Producto> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Producto> readAll() {
        return repo.findAll();
    }
}
