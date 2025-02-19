package com.ejemplo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false, length = 255) // Almacena la contraseña (se recomienda encriptarla)
    private String contrasena;

    @Column(nullable = false, updatable = false) // No debe modificarse después de la creación
    private LocalDateTime fecha_registro;

    // Constructor vacío
    public Usuario() {
        this.fecha_registro = LocalDateTime.now(); // Se asigna la fecha al crear el usuario
    }

    // Constructor con parámetros
    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fecha_registro = LocalDateTime.now(); // Se genera la fecha automáticamente
    }

    // Getters y Setters
    public Long getId() { return id_usuario; }
    public void setId(Long id) { this.id_usuario = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contrasena; }
    public void setContraseña(String contraseña) { this.contrasena = contraseña; }

    public LocalDateTime getFechaRegistro() { return fecha_registro; }
    public void setFechaRegistro(LocalDateTime fecha_registro) { this.fecha_registro = fecha_registro; }
}

