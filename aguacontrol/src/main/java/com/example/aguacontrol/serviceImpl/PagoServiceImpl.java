package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Pago;
import com.example.aguacontrol.repository.PagoRepository;
import com.example.aguacontrol.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {
    private final PagoRepository repo;

    @Override
    public Pago create(Pago pago) {
        return repo.save(pago);
    }

    @Override
    public Pago update(Pago pago) {
        return repo.save(pago);
    }

    @Override
    public Optional<Pago> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Pago> readAll() {
        return repo.findAll();
    }
}
