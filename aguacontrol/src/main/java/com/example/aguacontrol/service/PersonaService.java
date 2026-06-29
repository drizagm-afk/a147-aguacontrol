package com.example.aguacontrol.service;

import com.example.aguacontrol.dto.ClienteViewDTO;
import com.example.aguacontrol.dto.ClienteRegistryDTO;
import com.example.aguacontrol.error.ValidationErrors;
import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Persona;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface PersonaService extends CrudService<Persona, Long> {
    Optional<ClienteViewDTO> readCliente(Long id);

    Iterable<ClienteViewDTO> readAllClientes();

    Iterable<ClienteViewDTO> searchClientes(String keyword);

    //CLIENTE REGISTRY DTO
    ValidationErrors validateRegistry(ClienteRegistryDTO dto);

    @Transactional
    Persona registry(ClienteRegistryDTO dto);

    Optional<ClienteRegistryDTO> readAsRegistry(Long id);
}
