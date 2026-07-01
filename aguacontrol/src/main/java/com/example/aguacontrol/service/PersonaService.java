package com.example.aguacontrol.service;

import com.example.aguacontrol.dto.ClienteDTO;
import com.example.aguacontrol.utils.ValidationErrors;
import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Persona;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PersonaService extends CrudService<Persona, Long> {
    @Transactional(readOnly = true)
    Optional<ClienteDTO> readCliente(Long id);

    @Transactional(readOnly = true)
    List<ClienteDTO> browseClientes(String keyword);

    //CLIENTE REGISTRY
    ValidationErrors validateCliente(ClienteDTO dto);

    @Transactional
    Persona saveCliente(ClienteDTO dto);
}
