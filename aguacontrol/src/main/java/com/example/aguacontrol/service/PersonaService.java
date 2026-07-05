package com.example.aguacontrol.service;

import com.example.aguacontrol.dto.business.clients.ClienteDTO;
import com.example.aguacontrol.dto.business.clients.ClienteViewDTO;
import com.example.aguacontrol.utils.validation.ValidationErrors;
import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Persona;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PersonaService extends CrudService<Persona, Long> {
    //CLIENTE VIEW DTO
    @Transactional(readOnly = true)
    List<ClienteViewDTO> browseClientes(String keyword);

    //CLIENTE DTO
    @Transactional(readOnly = true)
    Optional<ClienteDTO> readCliente(Long id);

    ValidationErrors validateCliente(ClienteDTO dto);

    @Transactional
    Persona saveCliente(ClienteDTO dto);
}
