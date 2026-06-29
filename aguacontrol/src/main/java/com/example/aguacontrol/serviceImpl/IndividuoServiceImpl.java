package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Individuo;
import com.example.aguacontrol.repository.IndividuoRepository;
import com.example.aguacontrol.service.IndividuoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IndividuoServiceImpl implements IndividuoService {
    private final IndividuoRepository repo;

    @Override
    public Individuo create(Individuo individuo) {
        return repo.save(individuo);
    }

    @Override
    public Individuo update(Individuo individuo) {
        return repo.save(individuo);
    }

    @Override
    public Optional<Individuo> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Individuo> readAll() {
        return repo.findAll();
    }
}
