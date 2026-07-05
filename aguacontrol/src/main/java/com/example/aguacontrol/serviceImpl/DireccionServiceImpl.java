package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Direccion;
import com.example.aguacontrol.repository.DireccionRepository;
import com.example.aguacontrol.service.DireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DireccionServiceImpl implements DireccionService {
    private final DireccionRepository repo;

    @Override
    public Direccion create(Direccion direccion) {
        return repo.save(direccion);
    }

    @Override
    public Direccion update(Direccion direccion) {
        return repo.save(direccion);
    }

    @Override
    public Optional<Direccion> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Direccion> readAll() {
        return repo.findAll();
    }

    //CUSTOM
    @Override
    public Direccion ensure(String referencia) {
        return repo.findByReferencia(referencia).orElseGet(() -> {
            var direccion = new Direccion();
            direccion.setReferencia(referencia);
            direccion.setLatitud(BigDecimal.ZERO);
            direccion.setLongitud(BigDecimal.ZERO);

            return repo.save(direccion);
        });
    }
}
