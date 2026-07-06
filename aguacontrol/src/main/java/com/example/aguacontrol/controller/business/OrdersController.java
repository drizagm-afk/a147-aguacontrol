package com.example.aguacontrol.controller.business;

import com.example.aguacontrol.dto.ApiResponse;
import com.example.aguacontrol.dto.business.clients.ClienteDTO;
import com.example.aguacontrol.dto.business.orders.PedidoDTO;
import com.example.aguacontrol.dto.business.orders.PedidoUpdateDTO;
import com.example.aguacontrol.service.PedidoService;
import com.example.aguacontrol.service.PersonaService;
import com.example.aguacontrol.service.ProductoService;
import com.example.aguacontrol.utils.FormMode;
import com.example.aguacontrol.utils.toast.Toaster;
import com.example.aguacontrol.validator.EmpresaDTOValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/business/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final PedidoService serv;
    private final ProductoService productoServ;

    //PERSIST: CREATE
    @GetMapping
    public String orders(Model model) {
        var pedido = new PedidoDTO();
        model.addAttribute("mode", FormMode.CREATE);
        model.addAttribute("form", pedido);

        return "business/orders/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("form") PedidoDTO form,
            BindingResult errors, Model model, RedirectAttributes reModel
    ) {
        if (validateForm(form, errors)) {
            model.addAttribute("mode", FormMode.CREATE);
            return "business/orders/create";
        }
        serv.createPedido(form);

        new Toaster()
                .success("Pedido Registrado", "Pedido Registrado Exitosamente")
                .cook(reModel);

        return "redirect:/business/orders";
    }

    private boolean validateForm(PedidoDTO form, BindingResult errors) {
        //NON REPEATED PRODUCTS
        var productos = form.getProductos();

        Set<Long> ids = new HashSet<>();
        for (int i = 0; i < productos.size(); i++) {
            var id = productos.get(i).getId();

            if (id == null)
                continue;

            if (!ids.add(id)) {
                errors.rejectValue(
                        "productos[" + i + "].id",
                        "productos.notUnique",
                        "El Producto no puede repetirse"
                );
            }
        }

        //BUSINESS
        for (var error : serv.validatePedido(form).getErrors()) {
            errors.rejectValue(
                    error.field(),
                    "business.error",
                    error.message()
            );
        }

        return errors.hasErrors();
    }

    //VIEW
    @GetMapping("/manage")
    public String manage() {
        return "business/orders/orders";
    }

    @GetMapping("/browse")
    @ResponseBody
    public ApiResponse browse(@RequestParam("keyword") String keyword) {
        return ApiResponse.ok("Datos: ", serv.browsePedidos(keyword));
    }

    @GetMapping("/products/browse")
    @ResponseBody
    public ApiResponse browseProducts() {
        return ApiResponse.ok("Productos: ", productoServ.readAll());
    }

    //PERSIST: UPDATE
    private final String updateForm = "business/orders/update :: form";

    @GetMapping("/form/update/{id}")
    public String formUpdate(@PathVariable Long id, Model model) {
        var pedido = serv.readPedidoUpdate(id).orElseThrow();
        model.addAttribute("mode", FormMode.UPDATE);
        model.addAttribute("form", pedido);

        return updateForm;
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("form") PedidoUpdateDTO form,
            BindingResult errors, Model model, HttpServletResponse res
    ) {
        if (validateForm(form, errors)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            model.addAttribute("mode", FormMode.UPDATE);
            return updateForm;
        }
        serv.updatePedido(form);

        return updateForm;
    }

    private boolean validateForm(PedidoUpdateDTO form, BindingResult errors) {
        //BUSINESS
        for (var error : serv.validatePedidoUpdate(form).getErrors()) {
            errors.rejectValue(
                    error.field(),
                    "business.error",
                    error.message()
            );
        }

        return errors.hasErrors();
    }

    //CANCEL
    @DeleteMapping("/cancel/{id}")
    @ResponseBody
    public void cancel(@PathVariable Long id) {
        serv.cancelPedido(id);
    }
}
