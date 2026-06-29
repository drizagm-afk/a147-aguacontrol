package com.example.aguacontrol.controller.business;

import com.example.aguacontrol.controller.validator.EmpresaRegistryValidator;
import com.example.aguacontrol.dto.ApiResponse;
import com.example.aguacontrol.dto.ClienteViewDTO;
import com.example.aguacontrol.dto.ClienteRegistryDTO;
import com.example.aguacontrol.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/business/clients")
@RequiredArgsConstructor
public class ClientsController {
    private final PersonaService serv;

    private final EmpresaRegistryValidator empresaValidator;

    @GetMapping
    public String clients() {
        return "business/clients/view";
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestParam(value = "keyword") String keyword) {
        Iterable<ClienteViewDTO> clients;
        if (keyword != null && !keyword.trim().isEmpty()) {
            clients = serv.searchClientes(keyword.trim());
        } else {
            clients = serv.readAllClientes();
        }

        return ResponseEntity.ok(
                ApiResponse.ok("Datos: ", clients)
        );
    }

    @GetMapping("/form/create")
    public String formCreate(Model model) {
        model.addAttribute("form", new ClienteRegistryDTO());
        return "business/clients/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("form") ClienteRegistryDTO form,
            BindingResult errors
    ) {
        if (validateForm(form, errors))
            return "business/clients/create";
        serv.registry(form);

        return "redirect:/business/clients";
    }

    @GetMapping("/form/update/{id}")
    public String formUpdate(Model model, @PathVariable Long id) {
        var registry = serv.readAsRegistry(id).orElseThrow();

        model.addAttribute("form", registry);
        return "business/clients/update";
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("form") ClienteRegistryDTO form,
            BindingResult errors
    ) {
        if (validateForm(form, errors))
            return "business/clients/update";
        serv.registry(form);

        return "redirect:/business/clients";
    }

    private boolean validateForm(ClienteRegistryDTO form, BindingResult errors) {
        empresaValidator.validate(form, errors);

        for (var error : serv.validateRegistry(form).getErrors()) {
            errors.rejectValue(
                    error.field(),
                    "business.error",
                    error.message()
            );
        }

        return errors.hasErrors();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        serv.delete(id);
    }
}
