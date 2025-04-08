package com.dailyapp.loginapp.entities;

import jakarta.persistence.*; // Para anotaciones de JPA
import java.time.LocalDateTime; // Para manejar la fecha de registro

@Entity
@Table(name = "usuarios") 
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // <-- IMPORTANTE: Usa el nombre correcto de la base de datos
    private Long id;

    @Column(nullable = false) // No puede ser nulo
    private String nombre;

    @Column(nullable = false, unique = true) // No puede ser nulo y debe ser único
    private String correo;

    @Column(nullable = false) // No puede ser nulo
    private String contrasena;

    @Column(nullable = false, updatable = false) // No puede ser nulo y no se puede actualizar
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}

