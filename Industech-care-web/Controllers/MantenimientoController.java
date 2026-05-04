package com.industech.controllers;

import com.industech.dto.ApiResponse;
import com.industech.models.Mantenimiento;
import com.industech.models.Usuario;
import com.industech.services.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * CAMBIOS v2:
 *  - asignarTecnico / desasignarTecnico ahora reciben idUsuario (no idTecnico)
 *  - getTecnicosDeOrden devuelve List<Usuario> en vez de List<Tecnico>
 *  - Agrega endpoints de costos
 */
@RestController
@RequestMapping("/api/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    // ── CRUD ──────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<ApiResponse<List<Mantenimiento>>> listarTodos() {
        return ok(mantenimientoService.listarTodos(), "Mantenimientos obtenidos");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Mantenimiento>> buscarPorId(@PathVariable int id) {
        Mantenimiento m = mantenimientoService.buscarPorId(id);
        if (m == null) return notFound("Mantenimiento no encontrado");
        return ResponseEntity.ok(ApiResponse.success(m, "Mantenimiento encontrado"));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<Mantenimiento>>> listarPorEstado(
            @PathVariable String estado) {
        return ok(mantenimientoService.listarPorEstado(estado), "Mantenimientos por estado");
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponse<List<Mantenimiento>>> listarPorTipo(
            @PathVariable String tipo) {
        return ok(mantenimientoService.listarPorTipo(tipo), "Mantenimientos por tipo");
    }

    @GetMapping("/filtrar")
    public ResponseEntity<ApiResponse<List<Mantenimiento>>> filtrar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String texto) {
        return ok(mantenimientoService.filtrar(estado, tipo, texto), "Resultados del filtro");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Mantenimiento>> agregar(
            @RequestBody Mantenimiento m) {
        boolean ok = mantenimientoService.agregar(m);
        if (!ok) return serverError("No se pudo crear el mantenimiento");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(m, "Mantenimiento creado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Mantenimiento>> actualizar(
            @PathVariable int id, @RequestBody Mantenimiento m) {
        m.setId(id);
        boolean ok = mantenimientoService.actualizar(m);
        if (!ok) return notFound("Mantenimiento no encontrado");
        return ResponseEntity.ok(ApiResponse.success(m, "Mantenimiento actualizado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable int id) {
        boolean ok = mantenimientoService.eliminar(id);
        if (!ok) return notFound("Mantenimiento no encontrado");
        return ResponseEntity.ok(ApiResponse.success(null, "Mantenimiento eliminado"));
    }

    // ── Ciclo de vida ─────────────────────────────────────────────────────────

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<ApiResponse<Void>> iniciar(@PathVariable int id) {
        boolean ok = mantenimientoService.iniciar(id);
        if (!ok) return notFound("Mantenimiento no encontrado");
        return ResponseEntity.ok(ApiResponse.success(null, "Mantenimiento iniciado"));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ApiResponse<Void>> finalizar(@PathVariable int id) {
        boolean ok = mantenimientoService.finalizar(id);
        if (!ok) return notFound("Mantenimiento no encontrado o ya finalizado");
        return ResponseEntity.ok(ApiResponse.success(null, "Mantenimiento finalizado"));
    }

    // ── Técnicos de la orden (ahora referencia usuario) ───────────────────────

    // POST /api/mantenimientos/{idMant}/tecnicos
    // Body: { "idUsuario": 3 }
    @PostMapping("/{idMant}/tecnicos")
    public ResponseEntity<ApiResponse<Void>> asignarTecnico(
            @PathVariable int idMant,
            @RequestBody AsignarRequest req) {
        boolean ok = mantenimientoService.asignarTecnico(idMant, req.getIdUsuario());
        if (!ok) return serverError("No se pudo asignar el técnico");
        return ResponseEntity.ok(ApiResponse.success(null, "Técnico asignado"));
    }

    // DELETE /api/mantenimientos/{idMant}/tecnicos/{idUsuario}
    @DeleteMapping("/{idMant}/tecnicos/{idUsuario}")
    public ResponseEntity<ApiResponse<Void>> desasignarTecnico(
            @PathVariable int idMant, @PathVariable int idUsuario) {
        boolean ok = mantenimientoService.desasignarTecnico(idMant, idUsuario);
        if (!ok) return notFound("Técnico o mantenimiento no encontrado");
        return ResponseEntity.ok(ApiResponse.success(null, "Técnico desasignado"));
    }

    // GET /api/mantenimientos/{idMant}/tecnicos
    @GetMapping("/{idMant}/tecnicos")
    public ResponseEntity<ApiResponse<List<Usuario>>> getTecnicosDeOrden(
            @PathVariable int idMant) {
        return ok(mantenimientoService.getTecnicosDeOrden(idMant),
                "Técnicos de la orden");
    }

    // ── Costos ────────────────────────────────────────────────────────────────

    @PutMapping("/{id}/costos")
    public ResponseEntity<ApiResponse<Void>> actualizarCostos(
            @PathVariable int id, @RequestBody CostosRequest req) {
        boolean ok = mantenimientoService.actualizarCostos(
                id, req.getManoDeObra(), req.getRepuestos());
        if (!ok) return notFound("Mantenimiento no encontrado");
        return ResponseEntity.ok(ApiResponse.success(null, "Costos actualizados"));
    }

    @GetMapping("/costos/total")
    public ResponseEntity<ApiResponse<Double>> getCostoTotal() {
        return ok(mantenimientoService.getCostoTotalGeneral(), "Costo total");
    }

    @GetMapping("/costos/por-maquina")
    public ResponseEntity<ApiResponse<Map<Integer, Double>>> getCostosPorMaquina() {
        return ok(mantenimientoService.getCostosPorMaquina(), "Costos por máquina");
    }

    @GetMapping("/costos/por-tipo")
    public ResponseEntity<ApiResponse<Map<String, Double>>> getCostosPorTipo() {
        return ok(mantenimientoService.getCostosPorTipo(), "Costos por tipo");
    }

    // ── DTOs internos ─────────────────────────────────────────────────────────

    public static class AsignarRequest {
        private int idUsuario;
        public int getIdUsuario() { return idUsuario; }
        public void setIdUsuario(int v) { this.idUsuario = v; }
    }

    public static class CostosRequest {
        private double manoDeObra;
        private double repuestos;
        public double getManoDeObra() { return manoDeObra; }
        public void setManoDeObra(double v) { this.manoDeObra = v; }
        public double getRepuestos() { return repuestos; }
        public void setRepuestos(double v) { this.repuestos = v; }
    }

    // ── helpers ───────────────────────────────────────────────────────────────
    private <T> ResponseEntity<ApiResponse<T>> ok(T data, String msg) {
        return ResponseEntity.ok(ApiResponse.success(data, msg));
    }
    private <T> ResponseEntity<ApiResponse<T>> notFound(String msg) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(msg));
    }
    private <T> ResponseEntity<ApiResponse<T>> serverError(String msg) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(msg));
    }
}