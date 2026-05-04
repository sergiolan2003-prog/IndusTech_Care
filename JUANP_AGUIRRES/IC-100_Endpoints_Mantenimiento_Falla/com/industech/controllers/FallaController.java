package com.industech.controllers;

import com.industech.models.Falla;
import com.industech.services.FallaService;
import java.util.List;

/**
 * Controller de Fallas — recibe peticiones y delega al FallaService.
 * Aquí se expondrán las URLs que consume la interfaz (Web / App Móvil).
 */
public class FallaController {

    private final FallaService fallaService = new FallaService();

    public boolean agregar(Falla f)              { return fallaService.agregar(f); }
    public boolean actualizar(Falla f)           { return fallaService.actualizar(f); }
    public boolean eliminar(int idFalla)         { return fallaService.eliminar(idFalla); }
    public Falla buscarPorId(int id)             { return fallaService.buscarPorId(id); }
    public List<Falla> listarTodas()             { return fallaService.listarTodas(); }
    public List<Falla> listarPorGravedad(String gravedad) {
        return fallaService.listarPorGravedad(gravedad);
    }
}
