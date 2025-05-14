package com.dailyapp.loginapp.services;

import com.dailyapp.loginapp.entities.Gasto;
import com.dailyapp.loginapp.Repositories.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
    // Guardar un nuevo gasto
    public Gasto guardarGasto(Gasto gasto) {
        return gastoRepository.save(gasto);
    }
    // Obtener todos los gastos de un usuario por su ID
    public List<Gasto> obtenerGastosPorUsuario(Long usuarioId) {
        return gastoRepository.findByUsuarioId(usuarioId);
    }
}

