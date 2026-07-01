package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.dto.SignUpFormDTO;
import com.example.aguacontrol.model.Empresa;
import com.example.aguacontrol.model.Individuo;
import com.example.aguacontrol.model.Persona;
import com.example.aguacontrol.repository.EmpresaRepository;
import com.example.aguacontrol.repository.PersonaRepository;
import com.example.aguacontrol.utils.ValidationErrors;
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
    private final PersonaRepository personaRepo;
    private final EmpresaRepository empresaRepo;

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
    public ValidationErrors validateUser(SignUpFormDTO dto) {
        var id = (long) -1;

        var errors = new ValidationErrors();
        if (personaRepo.existsWithNombre(dto.getNombre(), id))
            errors.add("nombre", "El Nombre ya está registrado");
        if (repo.existsWithEmail(dto.getEmail(), id))
            errors.add("email", "El Email ya está registrado");
        if (dto.isEmpresa()) {
            var empresaDTO = dto.getEmpresaDTO();
            if (empresaRepo.existsWithRuc(empresaDTO.getRuc(), id))
                errors.add("empresaDTO.ruc", "El RUC ya está registrado");
        }

        return errors;
    }

    @Override
    public Usuario signUpUser(SignUpFormDTO dto) {
        Persona persona;
        if (dto.isEmpresa()) {
            var empresaDTO = dto.getEmpresaDTO();
            var empresa = new Empresa();
            empresa.setRuc(empresaDTO.getRuc());
            persona = empresa;
        } else {
            persona = new Individuo();
        }
        persona.setNombre(dto.getNombre().trim());

        //USUARIO
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail().trim().toLowerCase());
        usuario.setPassword(encoder.encode(dto.getPassword()));
        usuario.setRoles(Set.of(rolRepo.Cliente()));

        usuario.setPersona(persona);
        persona.setUsuario(usuario);

        personaRepo.save(persona);

        return usuario;
    }
}
