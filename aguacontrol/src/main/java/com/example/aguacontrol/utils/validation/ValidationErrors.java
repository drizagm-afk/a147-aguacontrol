package com.example.aguacontrol.utils.validation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class ValidationErrors {
    private final List<ValidationError> errors = new ArrayList<>();

    public void add(String field, String message) {
        errors.add(new ValidationError(field, message));
    }
}