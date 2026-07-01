package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.dto.ClienteDTO;
import com.example.aguacontrol.dto.EmpresaDTO;
import com.example.aguacontrol.model.*;
import com.example.aguacontrol.repository.DireccionRepository;
import com.example.aguacontrol.repository.TelefonoRepository;
import com.example.aguacontrol.utils.ValidationErrors;
import com.example.aguacontrol.repository.EmpresaRepository;
import com.example.aguacontrol.repository.PersonaRepository;
import com.example.aguacontrol.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository repo;
    private final EmpresaRepository empresaRepo;

    private final TelefonoRepository telefonoRepo;
    private final DireccionRepository direccionRepo;

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
    private ClienteDTO.TelefonoDTO mapToTelefono(Telefono telefono) {
        return new ClienteDTO.TelefonoDTO(
                telefono.getNumero()
        );
    }

    private ClienteDTO.DireccionDTO mapToDireccion(Direccion direccion) {
        return new ClienteDTO.DireccionDTO(
                direccion.getReferencia()
        );
    }

    private ClienteDTO mapToCliente(Persona persona, Long lastTelefonoId, Long lastDireccionId) {
        //TELEFONOS
        List<ClienteDTO.TelefonoDTO> telefonoDTOs = new ArrayList<>();
        for (var t : persona.getTelefonos()) {
            if (Objects.equals(t.getId(), lastTelefonoId))
                continue;

            telefonoDTOs.add(mapToTelefono(t));
        }
        persona.getTelefonos().stream()
                .filter(t -> Objects.equals(t.getId(), lastTelefonoId)).findFirst()
                .ifPresent(t -> telefonoDTOs.add(mapToTelefono(t)));

        //DIRECCIONES
        List<ClienteDTO.DireccionDTO> direccionDTOs = new ArrayList<>();
        for (var d : persona.getDirecciones()) {
            if (Objects.equals(d.getId(), lastDireccionId))
                continue;

            direccionDTOs.add(mapToDireccion(d));
        }
        persona.getDirecciones().stream()
                .filter(d -> Objects.equals(d.getId(), lastDireccionId)).findFirst()
                .ifPresent(d -> direccionDTOs.add(mapToDireccion(d)));

        //DTO
        EmpresaDTO empresaDTO = null;
        if (persona instanceof Empresa e) {
            empresaDTO = new EmpresaDTO();
            empresaDTO.setRuc(e.getRuc());
        }

        return new ClienteDTO(
                persona.getId(),
                String.format("CL-%03d", persona.getId()),
                persona.getNombre(),
                empresaDTO != null,
                empresaDTO,
                telefonoDTOs,
                direccionDTOs
        );
    }

    @Override
    public Optional<ClienteDTO> readCliente(Long id) {
        return read(id).map(p -> mapToCliente(
                p, repo.findLastUsedTelefonoId(id), repo.findLastUsedDireccionId(id)
        ));
    }

    @Override
    public List<ClienteDTO> browseClientes(String keyword) {
        List<Persona> personas;
        if (keyword == null || keyword.trim().isEmpty())
            personas = repo.browse(null);
        else
            personas = repo.browse("%" + keyword + "%");

        var ids = personas.stream().map(Persona::getId).toList();
        var telefonos = repo.findLastUsedTelefonoIds(ids);
        var direcciones = repo.findLastUsedDireccionIds(ids);

        return personas.stream()
                .map(p -> {
                    var telefonoId = telefonos.getOrDefault(p.getId(), null);
                    var direccionId = direcciones.getOrDefault(p.getId(), null);

                    return mapToCliente(p, telefonoId, direccionId);
                }).toList();
    }

    //CLIENTE REGISTRY DTO
    @Override
    public ValidationErrors validateCliente(ClienteDTO dto) {
        Long id = dto.getId();
        if (id == null)
            id = (long) -1;

        var errors = new ValidationErrors();
        if (repo.existsWithNombre(dto.getNombre(), id))
            errors.add("nombre", "El Nombre ya está registrado");
        if (dto.isEmpresa()) {
            var empresaDTO = dto.getEmpresaDTO();
            if (empresaRepo.existsWithRuc(empresaDTO.getRuc(), id))
                errors.add("empresaDTO.ruc", "El RUC ya está registrado");
        }

        return errors;
    }

    @Override
    public Persona saveCliente(ClienteDTO dto) {
        Long id = dto.getId();
        if (id == null)
            id = (long) -1;

        Persona persona = repo.findById(id).orElseGet(() -> {
            if (dto.isEmpresa())
                return new Empresa();

            return new Individuo();
        });

        if (persona instanceof Empresa e) {
            var empresaDTO = dto.getEmpresaDTO();
            e.setRuc(empresaDTO.getRuc());
        }
        persona.setNombre(dto.getNombre().trim());

        //TELEFONOS
        List<Telefono> telefonos = new ArrayList<>();
        for (var t : dto.getTelefonos()) {
            var optTelefono = telefonoRepo.findByNumero(t.getNumero());
            if (optTelefono.isPresent())
                telefonos.add(optTelefono.get());
            else {
                var telefono = new Telefono();
                telefono.setNumero(t.getNumero());
                telefonoRepo.save(telefono);

                telefonos.add(telefono);
            }
        }
        persona.setTelefonos(telefonos);

        //DIRECCIONES
        List<Direccion> direcciones = new ArrayList<>();
        for (var d : dto.getDirecciones()) {
            var optDireccion = direccionRepo.findByReferencia(d.getReferencia());
            if (optDireccion.isPresent())
                direcciones.add(optDireccion.get());
            else {
                var direccion = new Direccion();
                direccion.setReferencia(d.getReferencia());
                direccion.setLatitud(BigDecimal.ZERO);
                direccion.setLongitud(BigDecimal.ZERO);
                direccionRepo.save(direccion);

                direcciones.add(direccion);
            }
        }
        persona.setDirecciones(direcciones);

        return repo.save(persona);
    }
}
