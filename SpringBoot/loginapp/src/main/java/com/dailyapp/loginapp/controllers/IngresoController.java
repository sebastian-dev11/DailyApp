package com.dailyapp.loginapp.controllers;

import com.dailyapp.loginapp.entities.Ingreso;
import com.dailyapp.loginapp.entities.Usuario;
import com.dailyapp.loginapp.services.IngresoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;

    @PostMapping
    public ResponseEntity<String> agregarIngreso(@RequestBody Ingreso ingreso, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        ingreso.setUsuario(usuario);
        ingresoService.guardarIngreso(ingreso);
        return ResponseEntity.ok("Ingreso guardado correctamente");
    }

    @GetMapping
    public ResponseEntity<List<Ingreso>> listarIngresos(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return ResponseEntity.status(401).build();

        List<Ingreso> ingresos = ingresoService.obtenerIngresosPorUsuario(usuario.getId());
        return ResponseEntity.ok(ingresos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngreso(@PathVariable Long id) {
        ingresoService.eliminarIngreso(id);
        return ResponseEntity.noContent().build();
    }
}
