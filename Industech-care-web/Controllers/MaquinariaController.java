package com.industech.controllers;

import com.industech.dto.ApiResponse;
import com.industech.models.Maquinaria;
import com.industech.services.MaquinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maquinarias")
public class MaquinariaController {

    @Autowired
    private MaquinariaService maquinariaService;

    // GET /api/maquinarias
    @GetMapping
    public ResponseEntity<ApiResponse<List<Maquinaria>>> listarTodas() {
        return ResponseEntity.ok(
                ApiResponse.success(maquinariaService.listarTodas(), "Maquinarias obtenidas"));
    }

    // GET /api/maquinarias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Maquinaria>> buscarPorId(@PathVariable int id) {
        Maquinaria m = maquinariaService.buscarPorId(id);
        if (m == null) return notFound("Maquinaria no encontrada");
        return ResponseEntity.ok(ApiResponse.success(m, "Maquinaria encontrada"));
    }

    // GET /api/maquinarias/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<Maquinaria>>> listarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(
                ApiResponse.success(maquinariaService.listarPorEstado(estado), "OK"));
    }

    // GET /api/maquinarias/tipo/{tipo}
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponse<List<Maquinaria>>> listarPorTipo(
            @PathVariable String tipo) {
        return ResponseEntity.ok(
                ApiResponse.success(maquinariaService.listarPorTipo(tipo), "OK"));
    }

    // GET /api/maquinarias/filtrar?estado=&tipo=&texto=
    @GetMapping("/filtrar")
    public ResponseEntity<ApiResponse<List<Maquinaria>>> filtrar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String texto) {
        return ResponseEntity.ok(
                ApiResponse.success(maquinariaService.filtrar(estado, tipo, texto), "OK"));
    }

    // POST /api/maquinarias
    @PostMapping
    public ResponseEntity<ApiResponse<Maquinaria>> agregar(@RequestBody Maquinaria m) {
        boolean ok = maquinariaService.agregar(m);
        if (!ok) return serverError("No se pudo crear la maquinaria");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(m, "Maquinaria creada"));
    }

    // PUT /api/maquinarias/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Maquinaria>> actualizar(
            @PathVariable int id, @RequestBody Maquinaria m) {
        m.setIdMaquinaria(id);
        boolean ok = maquinariaService.actualizar(m);
        if (!ok) return notFound("Maquinaria no encontrada");
        return ResponseEntity.ok(ApiResponse.success(m, "Maquinaria actualizada"));
    }

    // PUT /api/maquinarias/{id}/estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Void>> cambiarEstado(
            @PathVariable int id, @RequestParam String nuevoEstado) {
        boolean ok = maquinariaService.cambiarEstado(id, nuevoEstado);
        if (!ok) return notFound("Maquinaria no encontrada");
        return ResponseEntity.ok(ApiResponse.success(null, "Estado actualizado"));
    }

    // DELETE /api/maquinarias/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable int id) {
        boolean ok = maquinariaService.eliminar(id);
        if (!ok) return notFound("Maquinaria no encontrada");
        return ResponseEntity.ok(ApiResponse.success(null, "Maquinaria eliminada"));
    }

    // ── helpers ───────────────────────────────────────────────────────────────
    private <T> ResponseEntity<ApiResponse<T>> notFound(String msg) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(msg));
    }
    private <T> ResponseEntity<ApiResponse<T>> serverError(String msg) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(msg));
    }
}