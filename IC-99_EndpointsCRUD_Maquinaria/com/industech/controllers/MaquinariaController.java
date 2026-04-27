package com.industech.controllers;

import com.industech.models.Maquinaria;
import com.industech.services.MaquinariaService;
import java.util.List;

public class MaquinariaController {

    private final MaquinariaService maquinariaService = new MaquinariaService();

    public boolean agregar(Maquinaria m)                       { return maquinariaService.agregar(m); }
    public boolean actualizar(Maquinaria m)                    { return maquinariaService.actualizar(m); }
    public boolean eliminar(int idMaquinaria)                  { return maquinariaService.eliminar(idMaquinaria); }
    public Maquinaria buscarPorId(int id)                      { return maquinariaService.buscarPorId(id); }
    public List<Maquinaria> listarTodas()                      { return maquinariaService.listarTodas(); }
    public List<Maquinaria> listarPorEstado(String estado)     { return maquinariaService.listarPorEstado(estado); }
    public boolean cambiarEstado(int id, String nuevoEstado)   { return maquinariaService.cambiarEstado(id, nuevoEstado); }
    public List<Maquinaria> listarPorTipo(String tipo)         { return maquinariaService.listarPorTipo(tipo); }
    public List<Maquinaria> filtrar(String estado, String tipo, String texto) {
        return maquinariaService.filtrar(estado, tipo, texto);
    }
}
