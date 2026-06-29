package com.example.aguacontrol.controller.validator;

import com.example.aguacontrol.dto.ClienteRegistryDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmpresaRegistryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ClienteRegistryDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var form = (ClienteRegistryDTO) target;
        if (!form.isEmpresa())
            return;

        var empresaForm = form.getEmpresaForm();
        if (empresaForm == null) {
            errors.rejectValue(
                    "empresaForm",
                    "empresaForm.required",
                    "Los Datos de Empresa son obligatorios"
            );
            return;
        }

        var ruc = empresaForm.getRuc();
        if (ruc == null || ruc.isBlank()) {
            errors.rejectValue(
                    "empresaForm.ruc",
                    "ruc.required",
                    "El RUC es obligatorio"
            );
        } else if (!ruc.matches("\\d{11}")) {
            errors.rejectValue(
                    "empresaForm.ruc",
                    "ruc.invalid",
                    "El RUC debe tener 11 dígitos"
            );
        }
    }
}
