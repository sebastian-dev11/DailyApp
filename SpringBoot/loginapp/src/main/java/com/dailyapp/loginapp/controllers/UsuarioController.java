package com.dailyapp.loginapp.controllers;

import com.dailyapp.loginapp.entities.Usuario;
import com.dailyapp.loginapp.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080") // Habilita CORS
@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Registrar usuario
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    // Iniciar sesión y guardar usuario en sesión
    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(@RequestBody Usuario usuario, HttpSession session) {
        boolean valido = usuarioService.validarCredenciales(usuario.getCorreo(), usuario.getContrasena());
        if (valido) {
            Optional<Usuario> usuarioOptional = usuarioService.findByCorreo(usuario.getCorreo());
            usuarioOptional.ifPresent(u -> session.setAttribute("usuario", u));
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    // Obtener nombre del usuario logueado
    @GetMapping("/usuario")
    public ResponseEntity<String> obtenerUsuarioSesion(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return ResponseEntity.ok(usuario.getNombre());
        } else {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }
    }

    // Endpoint GET de registro
    @GetMapping("/registro")
    public ResponseEntity<String> mostrarRegistro() {
        return ResponseEntity.ok("Endpoint de registro disponible");
    }

    // Endpoint GET de login
    @GetMapping("/login")
    public ResponseEntity<String> mostrarLogin() {
        return ResponseEntity.ok("Endpoint de login disponible");
    }
}






