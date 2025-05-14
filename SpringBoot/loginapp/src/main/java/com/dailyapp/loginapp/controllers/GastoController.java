package com.dailyapp.loginapp.controllers;

import com.dailyapp.loginapp.entities.Gasto;
import com.dailyapp.loginapp.entities.Usuario;
import com.dailyapp.loginapp.services.GastoService;
import com.dailyapp.loginapp.Repositories.GastoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
private GastoRepository GastoRepository;

    @PostMapping
public ResponseEntity<String> agregarGasto(@RequestBody Gasto gasto, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
    }

    gasto.setUsuario(usuario); // Asociar gasto con el usuario logueado
    GastoRepository.save(gasto);
    return ResponseEntity.ok("Gasto guardado exitosamente");
}

    @GetMapping
    public ResponseEntity<List<Gasto>> listarGastos(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return ResponseEntity.status(401).build();

        List<Gasto> gastos = gastoService.obtenerGastosPorUsuario(usuario.getId());
        return ResponseEntity.ok(gastos);
    }

    @PutMapping("/{id}")
public ResponseEntity<String> actualizarGasto(@PathVariable Long id, @RequestBody Gasto nuevoGasto, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
    }

    Gasto gastoExistente = GastoRepository.findById(id).orElse(null);
    if (gastoExistente == null || !gastoExistente.getUsuario().getId().equals(usuario.getId())) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gasto no encontrado o no autorizado");
    }

    gastoExistente.setCategoria(nuevoGasto.getCategoria());
    gastoExistente.setDescripcion(nuevoGasto.getDescripcion());
    gastoExistente.setMonto(nuevoGasto.getMonto());
    gastoExistente.setFecha(nuevoGasto.getFecha());

    GastoRepository.save(gastoExistente);
    return ResponseEntity.ok("Gasto actualizado correctamente");
}

@DeleteMapping("/{id}")
public ResponseEntity<String> eliminarGasto(@PathVariable Long id, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
    }

    Gasto gasto = GastoRepository.findById(id).orElse(null);
    if (gasto == null || !gasto.getUsuario().getId().equals(usuario.getId())) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gasto no encontrado o no autorizado");
    }

    GastoRepository.delete(gasto);
    return ResponseEntity.ok("Gasto eliminado exitosamente");
}

}
