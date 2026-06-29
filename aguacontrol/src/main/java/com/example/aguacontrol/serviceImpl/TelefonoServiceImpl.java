package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Telefono;
import com.example.aguacontrol.repository.TelefonoRepository;
import com.example.aguacontrol.service.TelefonoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelefonoServiceImpl implements TelefonoService {
    private final TelefonoRepository repo;

    @Override
    public Telefono create(Telefono telefono) {
        return repo.save(telefono);
    }

    @Override
    public Telefono update(Telefono telefono) {
        return repo.save(telefono);
    }

    @Override
    public Optional<Telefono> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Telefono> readAll() {
        return repo.findAll();
    }
}
