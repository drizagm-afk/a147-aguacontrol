package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Receta;
import com.example.aguacontrol.repository.RecetaRepository;
import com.example.aguacontrol.service.RecetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecetaServiceImpl implements RecetaService {
    private final RecetaRepository repo;

    @Override
    public Receta create(Receta receta) {
        return repo.save(receta);
    }

    @Override
    public Receta update(Receta receta) {
        return repo.save(receta);
    }

    @Override
    public Optional<Receta> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Receta> readAll() {
        return repo.findAll();
    }
}
