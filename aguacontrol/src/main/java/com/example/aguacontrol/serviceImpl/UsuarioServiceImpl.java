package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.dto.UsuarioRegistryDTO;
import com.example.aguacontrol.error.ValidationErrors;
import com.example.aguacontrol.model.Usuario;
import com.example.aguacontrol.repository.RolRepository;
import com.example.aguacontrol.repository.UsuarioRepository;
import com.example.aguacontrol.service.PersonaService;
import com.example.aguacontrol.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repo;
    private final RolRepository rolRepo;

    private final PersonaService personaServ;

    private final PasswordEncoder encoder;

    @Override
    public Usuario create(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public Optional<Usuario> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Usuario> readAll() {
        return repo.findAll();
    }

    //USUARIO REGISTRY DTO
    @Override
    public ValidationErrors validateRegistry(UsuarioRegistryDTO dto) {
        Long id = dto.getId();
        if (id == null)
            id = (long) -1;

        var errors = personaServ.validateRegistry(dto);
        if (repo.existsWithEmail(dto.getEmail(), id))
            errors.add("email", "El Email ya está registrado");

        return errors;
    }

    @Override
    public Usuario registry(UsuarioRegistryDTO form) {
        var persona = personaServ.registry(form);
        Usuario usuario = new Usuario();
        usuario.setEmail(form.getEmail().trim().toLowerCase());
        usuario.setPassword(encoder.encode(form.getPassword()));
        usuario.setRoles(Set.of(rolRepo.Cliente()));

        usuario.setPersona(persona);
        persona.setUsuario(usuario);

        personaServ.update(persona);

        return usuario;
    }
}
