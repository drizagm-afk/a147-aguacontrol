package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Insumo;
import com.example.aguacontrol.repository.InsumoRepository;
import com.example.aguacontrol.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsumoServiceImpl implements InsumoService {
    private final InsumoRepository repo;

    @Override
    public Insumo create(Insumo insumo) {
        return repo.save(insumo);
    }

    @Override
    public Insumo update(Insumo insumo) {
        return repo.save(insumo);
    }

    @Override
    public Optional<Insumo> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Insumo> readAll() {
        return repo.findAll();
    }
}
