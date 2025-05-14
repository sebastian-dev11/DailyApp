package com.dailyapp.loginapp.Repositories;

import com.dailyapp.loginapp.entities.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    List<Ingreso> findByUsuarioId(Long usuarioId);
}
