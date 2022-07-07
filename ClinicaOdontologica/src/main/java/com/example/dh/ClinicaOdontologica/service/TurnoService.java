package com.example.dh.ClinicaOdontologica.service;
import com.example.dh.ClinicaOdontologica.dto.TurnoDTO;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Turno;
import com.example.dh.ClinicaOdontologica.repository.ITurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TurnoService {

   private ITurnoRepository turnoRepository;
   OdontologoService odontologoService;
   PacienteService pacienteService;
    ObjectMapper mapper; //necesario para la conversión de la entidad a DTO y viceversa.
    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public TurnoService(ITurnoRepository turnoRepository, OdontologoService odontologoService, PacienteService pacienteService, ObjectMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.mapper = mapper;
    }

    //métodos
    //1. Guardar un turno en la base de datos.
    public Turno guardarTurno(Turno turno)throws BadRequestException, EntityNotFoundException{
        if(turno == null || turno.getPaciente()==null || turno.getOdontologo() == null) {
            throw new BadRequestException("Los datos ingresados para registrar el turno no son correctos o están incompletos. Debe incluir obligatoriamente: id del odontólogo y id del paciente.");
        }else {
            odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
            pacienteService.buscarPacientePorId(turno.getPaciente().getId());
            return turnoRepository.save(turno);
        }
    }

    //2. Registrar un turno en la base de datos.
    public Turno registrarTurno(Turno turno) throws BadRequestException, EntityNotFoundException{
        return guardarTurno(turno);
    }

    //3. Buscar un turno por id.
    public TurnoDTO buscarTurnoPorId(Long id) throws EntityNotFoundException{
        Optional<Turno> turno = turnoRepository.findById(id);
        TurnoDTO turnoDTO = null;
        if(turno.isEmpty()){
            throw new EntityNotFoundException("El turno con el id: " + id + " no se encuentra en la base de datos");
        } else{
            turnoDTO = mapper.convertValue(turno, TurnoDTO.class);
        }
        return turnoDTO;
    }

    //4. Obtener un listado de todos los turnos registrados en la DB.
    public Set<TurnoDTO> buscarTodosTurnos()throws EntityNotFoundException{
        List<Turno> turnos = turnoRepository.findAll();
        Set<TurnoDTO> turnosDTO = new HashSet<>();
        if(turnos.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran turnos en la base de datos");
        }else{
            for (Turno turno : turnos) {
                turnosDTO.add(mapper.convertValue(turno, TurnoDTO.class));
            }
        }
        return turnosDTO;
    }

    //5. Actualizar los datos de un turno.
    public Turno actualizarTurno(Turno turno) throws EntityNotFoundException, BadRequestException{
        buscarTurnoPorId(turno.getId());
        return guardarTurno(turno);
    }

    //6.Eliminar un turno por id.
    public void eliminarTurnoPorId(Long id) throws EntityNotFoundException{
        buscarTurnoPorId(id);
        turnoRepository.deleteById(id);
    }

    //7.Eliminar todos los turnos de la base de datos
    public void eliminarTodosTurnos() throws EntityNotFoundException{
        buscarTodosTurnos();
        turnoRepository.deleteAll();
    }
}
