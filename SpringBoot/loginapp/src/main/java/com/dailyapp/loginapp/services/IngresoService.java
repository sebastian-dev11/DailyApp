package com.dailyapp.loginapp.services;

import com.dailyapp.loginapp.entities.Ingreso;
import com.dailyapp.loginapp.Repositories.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    public void guardarIngreso(Ingreso ingreso) {
        ingresoRepository.save(ingreso);
    }

    public List<Ingreso> obtenerIngresosPorUsuario(Long usuarioId) {
        return ingresoRepository.findByUsuarioId(usuarioId);
    }

    public void eliminarIngreso(Long id) {
        ingresoRepository.deleteById(id);
    }
}
