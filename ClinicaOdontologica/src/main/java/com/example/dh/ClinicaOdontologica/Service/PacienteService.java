package com.example.dh.ClinicaOdontologica.Service;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

public class PacienteService {
    private IDaoRepository<Paciente> pacienteDao;
    public PacienteService(IDaoRepository<Paciente> pacienteDao){
        this.pacienteDao = pacienteDao;
    }

    //métodos
    //1. Registrar un paciente en la base de datos.
    public Paciente registrarPaciente(Paciente paciente) throws Exception{
        return pacienteDao.agregar(paciente);
    }
    //2. Buscar un paciente por id.
    public Paciente buscarPacientePorId(Long id) throws Exception{
        return pacienteDao.buscarPorId(id);
    }

    //3. Obtener un listado de todos los pacientes registrados en la DB.
    public List<Paciente> buscarTodosPacientes() throws Exception{
        return pacienteDao.buscarTodos();
    }

    //4. Actualizar los datos de un paciente.
    public Paciente actualizarPaciente(Paciente paciente) throws Exception{
        return pacienteDao.actualizar(paciente);
    }

    //5.Eliminar un paciente por id.
    public void eliminarPacientePorId(Long id) throws Exception{
        pacienteDao.eliminarPorId(id);
    }

    //6.Eliminar todos los pacientes de la base de datos
    public void eliminarTodosPacientes() throws Exception{
        pacienteDao.eliminarTodos();
    }
}
