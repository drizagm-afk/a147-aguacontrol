package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.dto.ClienteViewDTO;
import com.example.aguacontrol.dto.ClienteRegistryDTO;
import com.example.aguacontrol.dto.EmpresaRegistryDTO;
import com.example.aguacontrol.error.ValidationErrors;
import com.example.aguacontrol.model.Empresa;
import com.example.aguacontrol.model.Persona;
import com.example.aguacontrol.repository.EmpresaRepository;
import com.example.aguacontrol.repository.PersonaRepository;
import com.example.aguacontrol.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository repo;
    private final EmpresaRepository empresaRepo;

    @Override
    public Persona create(Persona persona) {
        return repo.save(persona);
    }

    @Override
    public Persona update(Persona persona) {
        return repo.save(persona);
    }

    @Override
    public Optional<Persona> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Persona> readAll() {
        return repo.findAll();
    }

    //CUSTOM CLIENTES
    private ClienteViewDTO mapToCliente(Persona persona) {
        return new ClienteViewDTO(
                persona.getId(),
                String.format("CL-%03d", persona.getId()),
                persona.getNombre(),
                persona instanceof Empresa,
                repo.findLastUsedTelefono(persona.getId()).orElse(null),
                repo.findLastUsedDireccion(persona.getId()).orElse(null)
        );
    }

    @Override
    public Optional<ClienteViewDTO> readCliente(Long id) {
        return read(id).map(this::mapToCliente);
    }

    @Override
    public Iterable<ClienteViewDTO> readAllClientes() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .map(this::mapToCliente)::iterator;
    }

    @Override
    public Iterable<ClienteViewDTO> searchClientes(String keyword) {
        return StreamSupport.stream(repo.search("%" + keyword + "%").spliterator(), false)
                .map(this::mapToCliente)::iterator;
    }

    //CLIENTE REGISTRY DTO
    @Override
    public ValidationErrors validateRegistry(ClienteRegistryDTO dto) {
        Long id = dto.getId();
        if (id == null)
            id = (long) -1;

        var errors = new ValidationErrors();
        if (repo.existsWithNombre(dto.getNombre(), id))
            errors.add("nombre", "El Nombre ya está registrado");
        if (dto.isEmpresa()) {
            var empresaForm = dto.getEmpresaForm();
            if (empresaRepo.existsWithRuc(empresaForm.getRuc(), id))
                errors.add("empresaForm.ruc", "El RUC ya está registrado");
        }

        return errors;
    }

    @Override
    public Persona registry(ClienteRegistryDTO dto) {
        Persona persona;
        if (dto.isEmpresa()) {
            var empresaForm = dto.getEmpresaForm();
            var empresa = new Empresa();
            empresa.setRuc(empresaForm.getRuc());
            persona = empresa;
        } else {
            persona = new Persona();
        }
        persona.setId(dto.getId());
        persona.setNombre(dto.getNombre().trim());

        return repo.save(persona);
    }

    @Override
    public Optional<ClienteRegistryDTO> readAsRegistry(Long id) {
        return repo.findById(id).map(p -> {
            var dto = new ClienteRegistryDTO();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setEmpresa(false);

            if (p instanceof Empresa e) {
                dto.setEmpresa(true);
                var empresaDto = new EmpresaRegistryDTO();
                empresaDto.setRuc(e.getRuc());

                dto.setEmpresaForm(empresaDto);
            }

            return dto;
        });
    }
}
