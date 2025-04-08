package com.dailyapp.loginapp.services;


import com.dailyapp.loginapp.entities.Usuario;
import com.dailyapp.loginapp.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);
    }

    public boolean validarCredenciales(String correo, String contrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(correo);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return passwordEncoder.matches(contrasena, usuario.getContrasena());
        }
        return false;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // ✅ MÉTODO NECESARIO PARA USARLO EN EL CONTROLADOR
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}

