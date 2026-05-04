package com.industech.controllers;

import com.industech.models.Tecnico;
import com.industech.services.TecnicoService;
import java.util.List;

public class TecnicoController {

    private final TecnicoService tecnicoService = new TecnicoService();

    public boolean agregar(Tecnico t)                          { return tecnicoService.agregar(t); }
    public boolean actualizar(Tecnico t)                       { return tecnicoService.actualizar(t); }
    public boolean eliminar(int idTecnico)                     { return tecnicoService.eliminar(idTecnico); }
    public Tecnico buscarPorId(int id)                         { return tecnicoService.buscarPorId(id); }
    public List<Tecnico> listarTodos()                         { return tecnicoService.listarTodos(); }
    public List<Tecnico> listarPorEspecialidad(String espec)   { return tecnicoService.listarPorEspecialidad(espec); }
}
