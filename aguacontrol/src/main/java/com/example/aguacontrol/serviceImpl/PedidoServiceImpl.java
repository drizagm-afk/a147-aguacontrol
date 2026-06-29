package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Pedido;
import com.example.aguacontrol.repository.PedidoRepository;
import com.example.aguacontrol.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository repo;

    @Override
    public Pedido create(Pedido pedido) {
        return repo.save(pedido);
    }

    @Override
    public Pedido update(Pedido pedido) {
        return repo.save(pedido);
    }

    @Override
    public Optional<Pedido> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Pedido> readAll() {
        return repo.findAll();
    }
}
