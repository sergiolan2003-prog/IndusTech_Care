package com.industech.controllers;

import com.industech.models.Alerta;
import com.industech.services.AlertaService;
import java.util.List;

public class AlertaController {

    private final AlertaService alertaService = new AlertaService();

    public boolean agregar(Alerta a)                              { return alertaService.agregar(a); }
    public Alerta  buscarPorId(int id)                            { return alertaService.buscarPorId(id); }
    public boolean desactivar(int id)                             { return alertaService.desactivar(id); }
    public boolean actualizar(int id, String msg, String tipo)    { return alertaService.actualizar(id, msg, tipo); }
    public void    enviarTodas()                                   { alertaService.enviarTodas(); }
    public List<Alerta> listarTodas()                             { return alertaService.listarTodas(); }
    public List<Alerta> listarActivas()                           { return alertaService.listarActivas(); }
}