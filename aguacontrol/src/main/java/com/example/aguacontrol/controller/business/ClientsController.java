package com.example.aguacontrol.controller.business;

import com.example.aguacontrol.dto.ClienteDTO;
import com.example.aguacontrol.utils.FormMode;
import com.example.aguacontrol.utils.Toaster;
import com.example.aguacontrol.validator.EmpresaDTOValidator;
import com.example.aguacontrol.dto.ApiResponse;
import com.example.aguacontrol.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/business/clients")
@RequiredArgsConstructor
public class ClientsController {
    private final PersonaService serv;

    private final EmpresaDTOValidator empresaValidator;

    @GetMapping
    public String clients() {
        return "business/clients/clients";
    }

    @GetMapping("/browse")
    public ResponseEntity<ApiResponse> browse(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(
                ApiResponse.ok("Datos: ", serv.browseClientes(keyword))
        );
    }

    @GetMapping("/form/create")
    public String formCreate(@RequestParam("isEmpresa") boolean isEmpresa, Model model) {
        var cliente = new ClienteDTO();
        cliente.setEmpresa(isEmpresa);

        model.addAttribute("mode", FormMode.CREATE);
        model.addAttribute("form", cliente);
        return "business/clients/form";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("form") ClienteDTO form,
            BindingResult errors, Model model, RedirectAttributes reModel
    ) {
        if (validateForm(form, errors)) {
            model.addAttribute("mode", FormMode.CREATE);
            return "business/clients/form";
        }
        serv.saveCliente(form);

        new Toaster()
                .success(String.format("%s \"%s\" Registrado Existosamente",
                        form.isEmpresa() ? "Empresa" : "Cliente", form.getNombre()))
                .cook(reModel);

        return "redirect:/business/clients";
    }

    @GetMapping("/form/update/{id}")
    public String formUpdate(@PathVariable Long id, Model model) {
        var cliente = serv.readCliente(id).orElseThrow();

        model.addAttribute("mode", FormMode.UPDATE);
        model.addAttribute("form", cliente);
        return "business/clients/form";
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("form") ClienteDTO form,
            BindingResult errors, Model model, RedirectAttributes reModel
    ) {
        if (validateForm(form, errors)) {
            model.addAttribute("mode", FormMode.UPDATE);
            return "business/clients/form";
        }
        serv.saveCliente(form);

        new Toaster()
                .success(String.format("%s \"%s\" Actualizado Existosamente",
                        form.isEmpresa() ? "Empresa" : "Cliente", form.getNombre()))
                .cook(reModel);

        return "redirect:/business/clients";
    }

    @GetMapping("/form/view/{id}")
    public String formView(@PathVariable Long id, Model model) {
        var cliente = serv.readCliente(id).orElseThrow();

        model.addAttribute("mode", FormMode.VIEW);
        model.addAttribute("form", cliente);
        return "business/clients/form";
    }

    private boolean validateForm(ClienteDTO form, BindingResult errors) {
        //EMPRESA
        empresaValidator.validate(new EmpresaDTOValidator.Data(
                form.isEmpresa(), form.getEmpresaDTO()
        ), errors);

        //TELEFONO
        var telefonos = form.getTelefonos();

        Set<String> numeros = new HashSet<>();
        for (int i = 0; i < telefonos.size(); i++) {
            var numero = telefonos.get(i).getNumero();

            if (numero == null || numero.isBlank())
                continue;

            if (!numeros.add(numero)) {
                errors.rejectValue(
                        "telefonos[" + i + "].numero",
                        "telefonos.notUnique",
                        "El Teléfono no puede repetirse"
                );
            }
        }

        //DIRECCION
        var direcciones = form.getDirecciones();

        Set<String> referencias = new HashSet<>();
        for (int i = 0; i < direcciones.size(); i++) {
            var referencia = direcciones.get(i).getReferencia();

            if (referencia == null || referencia.isBlank())
                continue;

            if (!referencias.add(referencia)) {
                errors.rejectValue(
                        "direcciones[" + i + "].referencia",
                        "direcciones.notUnique",
                        "La Dirección no puede repetirse"
                );
            }
        }

        //BUSINESS
        for (var error : serv.validateCliente(form).getErrors()) {
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
