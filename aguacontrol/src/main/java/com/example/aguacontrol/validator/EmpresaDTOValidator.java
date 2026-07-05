package com.example.aguacontrol.validator;

import com.example.aguacontrol.dto.business.clients.EmpresaDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmpresaDTOValidator implements Validator {
    public record Data(boolean isEmpresa, EmpresaDTO dto) {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Data.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        var data = (Data) target;
        if (!data.isEmpresa)
            return;

        var dto = data.dto;
        if (dto == null) {
            errors.rejectValue(
                    "empresaDTO",
                    "empresaDTO.required",
                    "Los Datos de Empresa son obligatorios"
            );
            return;
        }

        //RUC
        var ruc = dto.getRuc();
        if (ruc == null || ruc.isBlank()) {
            errors.rejectValue(
                    "empresaDTO.ruc",
                    "ruc.required",
                    "El RUC es obligatorio"
            );
        } else if (!ruc.matches("\\d{11}")) {
            errors.rejectValue(
                    "empresaDTO.ruc",
                    "ruc.invalid",
                    "El RUC debe tener 11 dígitos"
            );
        }
    }
}