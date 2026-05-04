package com.industech.controllers;

import com.industech.dto.ApiResponse;
import com.industech.models.Falla;
import com.industech.services.FallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CAMBIOS v2:
 *  - Agrega GET /api/fallas/activas
 *  - Agrega GET /api/fallas/maquinaria/{idMaquinaria}
 *  - Agrega PUT /api/fallas/{id}/cerrar
 */
@RestController
@RequestMapping("/api/fallas")
public class FallaController {

    @Autowired
    private FallaService fallaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Falla>>> listarTodas() {
        return ok(fallaService.listarTodas(), "Fallas obtenidas");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Falla>> buscarPorId(@PathVariable int id) {
        Falla f = fallaService.buscarPorId(id);
        if (f == null) return notFound("Falla no encontrada");
        return ResponseEntity.ok(ApiResponse.success(f, "Falla encontrada"));
    }

    @GetMapping("/activas")
    public ResponseEntity<ApiResponse<List<Falla>>> listarActivas() {
        return ok(fallaService.listarActivas(), "Fallas activas");
    }

    @GetMapping("/gravedad/{gravedad}")
    public ResponseEntity<ApiResponse<List<Falla>>> listarPorGravedad(
            @PathVariable String gravedad) {
        return ok(fallaService.listarPorGravedad(gravedad), "Fallas por gravedad");
    }

    @GetMapping("/maquinaria/{idMaquinaria}")
    public ResponseEntity<ApiResponse<List<Falla>>> listarPorMaquinaria(
            @PathVariable int idMaquinaria) {
        return ok(fallaService.listarPorMaquinaria(idMaquinaria),
                "Fallas de la maquinaria");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Falla>> agregar(@RequestBody Falla f) {
        boolean ok = fallaService.agregar(f);
        if (!ok) return serverError("No se pudo registrar la falla");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(f, "Falla registrada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Falla>> actualizar(
            @PathVariable int id, @RequestBody Falla f) {
        f.setId(id);
        boolean ok = fallaService.actualizar(f);
        if (!ok) return notFound("Falla no encontrada");
        return ResponseEntity.ok(ApiResponse.success(f, "Falla actualizada"));
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<ApiResponse<Void>> cerrar(@PathVariable int id) {
        boolean ok = fallaService.cerrar(id);
        if (!ok) return notFound("Falla no encontrada");
        return ResponseEntity.ok(ApiResponse.success(null, "Falla cerrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable int id) {
        boolean ok = fallaService.eliminar(id);
        if (!ok) return notFound("Falla no encontrada");
        return ResponseEntity.ok(ApiResponse.success(null, "Falla eliminada"));
    }

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