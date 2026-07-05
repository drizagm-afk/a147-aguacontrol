package com.example.aguacontrol.service;

import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Telefono;

public interface TelefonoService extends CrudService<Telefono, Long> {
    Telefono ensure(String numero);
}
