package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.dto.business.orders.PedidoDTO;
import com.example.aguacontrol.dto.business.orders.PedidoUpdateDTO;
import com.example.aguacontrol.dto.business.orders.PedidoViewDTO;
import com.example.aguacontrol.model.*;
import com.example.aguacontrol.repository.*;
import com.example.aguacontrol.service.DireccionService;
import com.example.aguacontrol.service.PedidoService;
import com.example.aguacontrol.service.TelefonoService;
import com.example.aguacontrol.utils.validation.ValidationErrors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository repo;
    private final EstadoPedidoRepository estadoRepo;

    private final PersonaRepository personaRepo;
    private final TelefonoService telefonoServ;
    private final DireccionService direccionServ;

    private final ProductoRepository productoRepo;

    private final EstadoPagoRepository estadoPagoRepo;
    private final MetodoPagoRepository metodoPagoRepo;

    @Override
    public Pedido create(Pedido pedido) {
        return repo.save(pedido);
    }

    @Override
    public Pedido update(Pedido pedido) {
        return repo.save(pedido);
    }

    @Override
    public Optional<Pedido> read(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Iterable<Pedido> readAll() {
        return repo.findAll();
    }

    //>>>> DTOS
    //PEDIDO VIEW DTO
    private PedidoViewDTO mapToPedidoView(Pedido pedido) {
        var id = pedido.getId();
        var persona = pedido.getPersona();

        return new PedidoViewDTO(
                id,
                String.format("ORD-%04d", id),
                persona.getNombre(),
                persona instanceof Empresa,
                String.join(", ", pedido.getProductos().stream()
                        .map(p -> p.getProducto().getNombre() + " (" + p.getCantidad() + ")")
                        .toList()
                ),
                pedido.getPago().getMonto(),
                pedido.getEstado().getNombre(),
                canUpdatePedido(pedido),
                canCancelPedido(pedido)
        );
    }

    @Override
    public List<PedidoViewDTO> browsePedidos() {
        return repo.browsePedidos().stream().map(this::mapToPedidoView).toList();
    }

    //PEDIDO DTO
    @Override
    public ValidationErrors validatePedido(PedidoDTO dto) {
        var errors = new ValidationErrors();

        if (!personaRepo.existsWithNombre(dto.getCliente(), (long) -1))
            errors.add("cliente", "El Cliente no existe");

        //PRODUCTS
        var products = dto.getProductos();
        for (int i = 0; i < products.size(); i++) {
            var product = products.get(i);
            var id = product.getId();

            if (!productoRepo.existsById(id)) {
                errors.add("productos[" + i + "].id", "El Producto no existe");
                continue;
            }

            if (product.getCantidad() > productoRepo.findStock(id)) {
                errors.add(
                        "productos[" + i + "].cantidad",
                        "No hay suficiente Stock (" + product.getCantidad() + ")"
                );
            }
        }

        //PAGO
        if (metodoPagoRepo.existsById(dto.getMetodoPagoId()))
            errors.add("metodoPagoId", "El Metodo de Pago no existe");

        return errors;
    }

    private void setTelefono(Pedido pedido, String numero) {
        var persona = pedido.getPersona();
        var telefono = persona.getTelefonos().stream()
                .filter(t -> t.getNumero().equals(numero))
                .findFirst().orElseGet(() -> {
                    var t = telefonoServ.ensure(numero);
                    persona.getTelefonos().add(t);

                    return t;
                });
        pedido.setTelefono(telefono);
    }

    private void setDireccion(Pedido pedido, String referencia) {
        var persona = pedido.getPersona();
        var direccion = persona.getDirecciones().stream()
                .filter(d -> d.getReferencia().equals(referencia))
                .findFirst().orElseGet(() -> {
                    var d = direccionServ.ensure(referencia);
                    persona.getDirecciones().add(d);

                    return d;
                });
        pedido.setDireccion(direccion);
    }

    @Override
    public Pedido createPedido(PedidoDTO dto) {
        var pedido = new Pedido();
        pedido.setEstado(estadoRepo.Programado());

        //PERSONA
        var persona = personaRepo.findByNombre(dto.getCliente()).orElseThrow();
        pedido.setPersona(persona);

        setTelefono(pedido, dto.getTelefono());
        setDireccion(pedido, dto.getDireccion());

        //PRODUCTOS
        var total = BigDecimal.ZERO;

        List<ProductoPedido> productos = new ArrayList<>();
        for (var productoDTO : dto.getProductos()) {
            var productoPe = new ProductoPedido();
            productoPe.setPedido(pedido);

            var producto = productoRepo.findById(productoDTO.getId()).orElseThrow();
            productoPe.setProducto(producto);
            var cantidad = productoDTO.getCantidad();
            productoPe.setCantidad(cantidad);
            var precioUnit = producto.getPrecio();
            productoPe.setPrecioUnitario(precioUnit);

            total = total.add(precioUnit.multiply(new BigDecimal(cantidad)));
            productos.add(productoPe);
        }
        pedido.setProductos(productos);

        //PAGO
        var pago = new Pago();
        pago.setMonto(total);
        pago.setEstado(estadoPagoRepo.Pendiente());
        pago.setMetodo(metodoPagoRepo.findById(dto.getMetodoPagoId()).orElseThrow());

        pedido.setPago(pago);

        return repo.save(pedido);
    }

    //PEDIDO UPDATE DTO
    private static final String[] updateEstados = {
            EstadoPedido.EnEspera,
            EstadoPedido.Programado
    };
    private boolean canUpdatePedido(Long id) {
        return repo.isEstadoIn(id, updateEstados);
    }
    private boolean canUpdatePedido(Pedido pedido) {
        return pedido.getEstado().isIn(updateEstados);
    }

    @Override
    public Optional<PedidoUpdateDTO> readPedidoUpdate(Long id) {
        return repo.findById(id).map(p -> new PedidoUpdateDTO(
                p.getId(),
                p.getPersona().getId(),
                p.getEstado().getNombre(),
                p.getTelefono().getNumero(),
                p.getDireccion().getReferencia()
        ));
    }

    @Override
    public ValidationErrors validatePedidoUpdate(PedidoUpdateDTO dto) {
        return new ValidationErrors();
    }

    @Override
    public Pedido updatePedido(PedidoUpdateDTO dto) {
        Long id = dto.getId();

        if (!canUpdatePedido(id))
            throw new RuntimeException("El Pedido no se puede actualizar en su Estado actual");

        var pedido = repo.findById(id).orElseThrow();
        setTelefono(pedido, dto.getTelefono());
        setDireccion(pedido, dto.getDireccion());

        return repo.save(pedido);
    }

    //CANCEL PEDIDO
    private static final String[] cancelEstados = {
            EstadoPedido.EnEspera,
            EstadoPedido.Programado,
            EstadoPedido.EnRuta
    };
    private boolean canCancelPedido(Long id) {
        return repo.isEstadoIn(id, cancelEstados);
    }
    private boolean canCancelPedido(Pedido pedido) {
        return pedido.getEstado().isIn(cancelEstados);
    }

    @Override
    public void cancelPedido(Long id) {
        if (!canCancelPedido(id))
            throw new RuntimeException("El Pedido no se puede cancelar en su Estado actual");

        var pedido = repo.findById(id).orElseThrow();
        pedido.setEstado(estadoRepo.Cancelado());

        repo.save(pedido);
    }
}
