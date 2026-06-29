package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Rol;
import com.example.aguacontrol.repository.RolRepository;
import com.example.aguacontrol.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {
    private final RolRepository repo;

    @Override
    public Rol create(Rol rol) {
        return repo.save(rol);
    }

    @Override
    public Rol update(Rol rol) {
        return repo.save(rol);
    }

    @Override
    public Optional<Rol> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Rol> readAll() {
        return repo.findAll();
    }
}
