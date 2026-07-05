package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.dto.business.clients.ClienteDTO;
import com.example.aguacontrol.dto.business.clients.ClienteViewDTO;
import com.example.aguacontrol.dto.business.clients.EmpresaDTO;
import com.example.aguacontrol.model.*;
import com.example.aguacontrol.repository.DireccionRepository;
import com.example.aguacontrol.repository.TelefonoRepository;
import com.example.aguacontrol.service.DireccionService;
import com.example.aguacontrol.service.TelefonoService;
import com.example.aguacontrol.utils.validation.ValidationErrors;
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

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository repo;
    private final EmpresaRepository empresaRepo;

    private final TelefonoService telefonoServ;
    private final DireccionService direccionServ;

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

    //>>>> DTOS
    //CLIENTE VIEW DTO
    private ClienteViewDTO mapToClienteView(Persona persona, Long lastTelefonoId, Long lastDireccionId) {
        return new ClienteViewDTO(
                persona.getId(),
                String.format("CL-%04d", persona.getId()),
                persona.getNombre(),
                persona instanceof Empresa,
                persona.getTelefonos().stream()
                        .filter(t -> Objects.equals(t.getId(), lastTelefonoId))
                        .map(Telefono::getNumero).findFirst().orElse(""),
                persona.getDirecciones().stream()
                        .filter(d -> Objects.equals(d.getId(), lastDireccionId))
                        .map(Direccion::getReferencia).findFirst().orElse("")
        );
    }

    @Override
    public List<ClienteViewDTO> browseClientes(String keyword) {
        List<Persona> personas;
        if (keyword == null || keyword.trim().isEmpty())
            personas = repo.browse(null);
        else
            personas = repo.browse("%" + keyword + "%");

        var ids = personas.stream().map(Persona::getId).toList();
        var telefonos = repo.findLastUsedTelefonoIds(ids);
        var direcciones = repo.findLastUsedDireccionIds(ids);

        return personas.stream()
                .map(p -> mapToClienteView(
                        p,
                        telefonos.getOrDefault(p.getId(), null),
                        direcciones.getOrDefault(p.getId(), null)
                )).toList();
    }

    //CLIENTE DTO
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
        for (var t : dto.getTelefonos())
            telefonos.add(telefonoServ.ensure(t.getNumero()));

        persona.setTelefonos(telefonos);

        //DIRECCIONES
        List<Direccion> direcciones = new ArrayList<>();
        for (var d : dto.getDirecciones())
            direcciones.add(direccionServ.ensure(d.getReferencia()));

        persona.setDirecciones(direcciones);

        return repo.save(persona);
    }
}
