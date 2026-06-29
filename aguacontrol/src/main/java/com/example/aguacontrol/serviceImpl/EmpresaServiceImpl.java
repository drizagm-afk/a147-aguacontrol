package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Empresa;
import com.example.aguacontrol.repository.EmpresaRepository;
import com.example.aguacontrol.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {
    private final EmpresaRepository repo;

    @Override
    public Empresa create(Empresa empresa) {
        return repo.save(empresa);
    }

    @Override
    public Empresa update(Empresa empresa) {
        return repo.save(empresa);
    }

    @Override
    public Optional<Empresa> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Empresa> readAll() {
        return repo.findAll();
    }

    //CUSTOM
    @Override
    public boolean existsByRuc(String ruc) {
        return repo.findByRuc(ruc).isPresent();
    }
}
