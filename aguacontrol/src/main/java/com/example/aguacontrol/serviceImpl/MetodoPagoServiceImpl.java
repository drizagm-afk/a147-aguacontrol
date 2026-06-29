package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.MetodoPago;
import com.example.aguacontrol.repository.MetodoPagoRepository;
import com.example.aguacontrol.service.MetodoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetodoPagoServiceImpl implements MetodoPagoService {
    private final MetodoPagoRepository repo;

    @Override
    public MetodoPago create(MetodoPago metodoPago) {
        return repo.save(metodoPago);
    }

    @Override
    public MetodoPago update(MetodoPago metodoPago) {
        return repo.save(metodoPago);
    }

    @Override
    public Optional<MetodoPago> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<MetodoPago> readAll() {
        return repo.findAll();
    }
}
