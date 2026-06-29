package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Permiso;
import com.example.aguacontrol.repository.PermisoRepository;
import com.example.aguacontrol.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermisoServiceImpl implements PermisoService {
    private final PermisoRepository repo;

    @Override
    public Permiso create(Permiso permiso) {
        return repo.save(permiso);
    }

    @Override
    public Permiso update(Permiso permiso) {
        return repo.save(permiso);
    }

    @Override
    public Optional<Permiso> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Permiso> readAll() {
        return repo.findAll();
    }
}
