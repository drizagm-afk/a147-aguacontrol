package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.EstadoPago;
import com.example.aguacontrol.repository.EstadoPagoRepository;
import com.example.aguacontrol.service.EstadoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoPagoServiceImpl implements EstadoPagoService {
    private final EstadoPagoRepository repo;

    @Override
    public EstadoPago create(EstadoPago estadoPago) {
        return repo.save(estadoPago);
    }

    @Override
    public EstadoPago update(EstadoPago estadoPago) {
        return repo.save(estadoPago);
    }

    @Override
    public Optional<EstadoPago> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<EstadoPago> readAll() {
        return repo.findAll();
    }
}
