package com.example.aguacontrol.controller;

import com.example.aguacontrol.controller.validator.EmpresaRegistryValidator;
import com.example.aguacontrol.dto.UsuarioRegistryDTO;
import com.example.aguacontrol.service.EmpresaService;
import com.example.aguacontrol.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final UsuarioService serv;
    private final EmpresaService empresaServ;

    private final EmpresaRegistryValidator empresaValidator;

    @GetMapping
    public String form(Model model) {
        model.addAttribute("form", new UsuarioRegistryDTO());
        return "signup/form";
    }

    @PostMapping("/submit")
    public String signUp(
            @Valid @ModelAttribute("form") UsuarioRegistryDTO form,
            BindingResult errors
    ) {
        empresaValidator.validate(form, errors);

        //VALIDATE
        for (var error : serv.validateRegistry(form).getErrors()) {
            errors.rejectValue(
                    error.field(),
                    "business.error",
                    error.message()
            );
        }

        if (errors.hasErrors()) {
            form.setPassword(null);
            return "signup/form";
        }
        serv.registry(form);

        return "redirect:/login?registered";
    }
}
