package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.EstadoPedido;
import com.example.aguacontrol.repository.EstadoPedidoRepository;
import com.example.aguacontrol.service.EstadoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoPedidoServiceImpl implements EstadoPedidoService {
    private final EstadoPedidoRepository repo;

    @Override
    public EstadoPedido create(EstadoPedido estadoPedido) {
        return repo.save(estadoPedido);
    }

    @Override
    public EstadoPedido update(EstadoPedido estadoPedido) {
        return repo.save(estadoPedido);
    }

    @Override
    public Optional<EstadoPedido> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<EstadoPedido> readAll() {
        return repo.findAll();
    }
}
