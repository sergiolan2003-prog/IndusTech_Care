package com.industech.controllers;

import com.industech.dto.ApiResponse;
import com.industech.models.Repuesto;
import com.industech.services.RepuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    @Autowired
    private RepuestoService repuestoService;

    // GET /api/repuestos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Repuesto>>> listarTodos() {
        return ResponseEntity.ok(
                ApiResponse.success(repuestoService.listarTodos(), "Repuestos obtenidos"));
    }

    // GET /api/repuestos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Repuesto>> buscarPorId(@PathVariable int id) {
        Repuesto r = repuestoService.buscarPorId(id);
        if (r == null) return notFound("Repuesto no encontrado");
        return ResponseEntity.ok(ApiResponse.success(r, "Repuesto encontrado"));
    }

    // GET /api/repuestos/buscar?texto=bomba
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Repuesto>>> buscarPorNombre(
            @RequestParam String texto) {
        return ResponseEntity.ok(
                ApiResponse.success(repuestoService.buscarPorNombre(texto), "OK"));
    }

    // POST /api/repuestos
    @PostMapping
    public ResponseEntity<ApiResponse<Repuesto>> agregar(@RequestBody Repuesto r) {
        boolean ok = repuestoService.agregar(r);
        if (!ok) return serverError("No se pudo crear el repuesto");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(r, "Repuesto creado"));
    }

    // PUT /api/repuestos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Repuesto>> actualizar(
            @PathVariable int id, @RequestBody Repuesto r) {
        r.setIdRepuesto(id);
        boolean ok = repuestoService.actualizar(r);
        if (!ok) return notFound("Repuesto no encontrado");
        return ResponseEntity.ok(ApiResponse.success(r, "Repuesto actualizado"));
    }

    // DELETE /api/repuestos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable int id) {
        boolean ok = repuestoService.eliminar(id);
        if (!ok) return notFound("Repuesto no encontrado");
        return ResponseEntity.ok(ApiResponse.success(null, "Repuesto eliminado"));
    }

    private <T> ResponseEntity<ApiResponse<T>> notFound(String msg) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(msg));
    }
    private <T> ResponseEntity<ApiResponse<T>> serverError(String msg) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(msg));
    }
}