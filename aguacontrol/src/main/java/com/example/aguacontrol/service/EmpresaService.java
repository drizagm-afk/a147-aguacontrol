package com.example.aguacontrol.service;

import com.example.aguacontrol.generic.CrudService;
import com.example.aguacontrol.model.Empresa;

public interface EmpresaService extends CrudService<Empresa, Long> {
    boolean existsByRuc(String ruc);
}
