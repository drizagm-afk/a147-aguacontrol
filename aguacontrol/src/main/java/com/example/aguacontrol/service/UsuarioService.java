package com.example.aguacontrol.service;

import com.example.aguacontrol.dto.UsuarioRegistryDTO;
import com.example.aguacontrol.error.ValidationErrors;
import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Usuario;
import jakarta.transaction.Transactional;

public interface UsuarioService extends CrudService<Usuario, Long> {
    //USUARIO REGISTRY DTO
    ValidationErrors validateRegistry(UsuarioRegistryDTO dto);

    @Transactional
    Usuario registry(UsuarioRegistryDTO dto);
}
