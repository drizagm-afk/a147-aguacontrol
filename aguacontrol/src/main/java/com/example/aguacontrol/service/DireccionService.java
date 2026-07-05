package com.example.aguacontrol.service;

import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Direccion;
import com.example.aguacontrol.model.Telefono;

public interface DireccionService extends CrudService<Direccion, Long> {
    Direccion ensure(String referencia);
}
