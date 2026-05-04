package com.industech.controllers;

import com.industech.models.Repuesto;
import com.industech.services.RepuestoService;
import java.util.List;

public class RepuestoController {

    private final RepuestoService repuestoService = new RepuestoService();

    public List<Repuesto> listarTodos()                   { return repuestoService.listarTodos(); }
    public Repuesto buscarPorId(int id)                   { return repuestoService.buscarPorId(id); }
    public boolean agregar(Repuesto r)                    { return repuestoService.agregar(r); }
    public boolean eliminar(int id)                       { return repuestoService.eliminar(id); }
    public boolean actualizar(Repuesto r)                 { return repuestoService.actualizar(r); }
    public List<Repuesto> buscarPorNombre(String texto)   { return repuestoService.buscarPorNombre(texto); }
}
