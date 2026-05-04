package com.industech.controllers;

import com.industech.models.Mantenimiento;
import com.industech.models.Repuesto;
import com.industech.models.RepuestoOrden;
import com.industech.models.Tecnico;
import com.industech.services.MantenimientoService;
import java.util.List;
import java.util.Map;

public class MantenimientoController {

    private final MantenimientoService mantenimientoService = new MantenimientoService();

    public boolean agregar(Mantenimiento m)              { return mantenimientoService.agregar(m); }
    public boolean actualizar(Mantenimiento m)           { return mantenimientoService.actualizar(m); }
    public boolean eliminar(int id)                      { return mantenimientoService.eliminar(id); }
    public Mantenimiento buscarPorId(int id)             { return mantenimientoService.buscarPorId(id); }
    public List<Mantenimiento> listarTodos()             { return mantenimientoService.listarTodos(); }
    public boolean iniciar(int id)                       { return mantenimientoService.iniciar(id); }
    public boolean finalizar(int id)                     { return mantenimientoService.finalizar(id); }
    public List<Mantenimiento> listarPorEstado(String e) { return mantenimientoService.listarPorEstado(e); }
    public List<Mantenimiento> listarPorTipo(String t)   { return mantenimientoService.listarPorTipo(t); }

    public List<Mantenimiento> filtrar(String estado, String tipo, String texto) {
        return mantenimientoService.filtrar(estado, tipo, texto);
    }

    public boolean asignarTecnico(int idMant, Tecnico tec) {
        return mantenimientoService.asignarTecnico(idMant, tec);
    }
    public boolean desasignarTecnico(int idMant, int idTec) {
        return mantenimientoService.desasignarTecnico(idMant, idTec);
    }
    public List<Tecnico> getTecnicosDeOrden(int idMant) {
        return mantenimientoService.getTecnicosDeOrden(idMant);
    }

    public double getCostoTotalGeneral()              { return mantenimientoService.getCostoTotalGeneral(); }
    public Map<Integer, Double> getCostosPorMaquina() { return mantenimientoService.getCostosPorMaquina(); }
    public Map<String,  Double> getCostosPorTipo()    { return mantenimientoService.getCostosPorTipo(); }

    public boolean actualizarCostos(int id, double mano, double rep) {
        return mantenimientoService.actualizarCostos(id, mano, rep);
    }
    public boolean enlazarRepuesto(int idMant, Repuesto rep, int cant) {
        return mantenimientoService.enlazarRepuesto(idMant, rep, cant);
    }
    public boolean quitarRepuesto(int idMant, RepuestoOrden ro) {
        return mantenimientoService.quitarRepuesto(idMant, ro);
    }
    public List<RepuestoOrden> getRepuestosDeOrden(int idMant) {
        return mantenimientoService.getRepuestosDeOrden(idMant);
    }
}
