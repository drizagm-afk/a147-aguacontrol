package com.example.aguacontrol.controller;

import com.example.aguacontrol.validator.EmpresaDTOValidator;
import com.example.aguacontrol.dto.SignUpFormDTO;
import com.example.aguacontrol.service.EmpresaService;
import com.example.aguacontrol.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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

    private final EmpresaDTOValidator empresaValidator;

    @GetMapping
    public String form(Model model) {
        model.addAttribute("form", new SignUpFormDTO());
        return "signup/form";
    }

    @PostMapping("/register")
    public String signUp(
            @Valid @ModelAttribute("form") SignUpFormDTO form,
            BindingResult errors,
            HttpServletRequest http
    ) {
        empresaValidator.validate(new EmpresaDTOValidator.Data(
                form.isEmpresa(), form.getEmpresaDTO()
        ), errors);

        //VALIDATE
        for (var error : serv.validateUser(form).getErrors()) {
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
        serv.signUpUser(form);

        //AUTO SIGN IN
        try {
            http.login(form.getNombre(), form.getPassword());
        } catch (ServletException e) {
            return "redirect:/login";
        }

        return "redirect:/home";
    }
}
