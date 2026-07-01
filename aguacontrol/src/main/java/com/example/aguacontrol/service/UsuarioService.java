package com.example.aguacontrol.service;

import com.example.aguacontrol.dto.SignUpFormDTO;
import com.example.aguacontrol.utils.ValidationErrors;
import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Usuario;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioService extends CrudService<Usuario, Long> {
    //USUARIO SIGNUP
    ValidationErrors validateUser(SignUpFormDTO dto);

    @Transactional
    Usuario signUpUser(SignUpFormDTO dto);
}
