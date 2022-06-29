package com.example.dh.ClinicaOdontologica.Service;
import com.example.dh.ClinicaOdontologica.DTO.PacienteDTO;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Repository.IPacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PacienteService {
    private IPacienteRepository pacienteRepository;
    ObjectMapper mapper;//necesario para la conversión de la entidad a DTO y viceversa.

    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public PacienteService(IPacienteRepository pacienteRepository, ObjectMapper mapper) {
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    //métodos
    //1. Guardar un paciente en la base de datos.
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    //2. Registrar un paciente en la base de datos.
    public Paciente registrarPaciente(Paciente paciente){
        return guardarPaciente(paciente);
    }
    //3. Buscar un paciente por id.
    public PacienteDTO buscarPacientePorId(Long id){
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        PacienteDTO pacienteDTO = null;
        if(paciente.isPresent()){
            pacienteDTO = mapper.convertValue(paciente, PacienteDTO.class);
        }
        return pacienteDTO;
    }

    //4. Obtener un listado de todos los pacientes registrados en la DB.
    public Set<PacienteDTO> buscarTodosPacientes(){
        List<Paciente> pacientes = pacienteRepository.findAll();
        Set<PacienteDTO> pacientesDTO = new HashSet<>();
        for (Paciente paciente : pacientes) {
            pacientesDTO.add(mapper.convertValue(paciente, PacienteDTO.class));
        }
        return pacientesDTO;
    }

    //5. Actualizar los datos de un paciente.
    public Paciente actualizarPaciente(Paciente paciente){
        return guardarPaciente(paciente);
    }

    //6.Eliminar un paciente por id.
    public void eliminarPacientePorId(Long id){
        pacienteRepository.deleteById(id);
    }

    //7.Eliminar todos los pacientes de la base de datos
    public void eliminarTodosPacientes(){
        pacienteRepository.deleteAll();
    }
}
