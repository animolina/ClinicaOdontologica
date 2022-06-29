package com.example.dh.ClinicaOdontologica.Service;
import com.example.dh.ClinicaOdontologica.DTO.TurnoDTO;
import com.example.dh.ClinicaOdontologica.Model.Turno;
import com.example.dh.ClinicaOdontologica.Repository.ITurnoRepository;
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
    ObjectMapper mapper; //necesario para la conversión de la entidad a DTO y viceversa.
    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public TurnoService(ITurnoRepository turnoRepository, ObjectMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.mapper = mapper;
    }
    //métodos
    //1. Guardar un turno en la base de datos.
    public Turno guardarTurno(Turno turno){
        return turnoRepository.save(turno);
    }

    //2. Registrar un turno en la base de datos.
    public Turno registrarTurno(Turno turno){
        return guardarTurno(turno);
    }
    //3. Buscar un turno por id.
    public TurnoDTO buscarTurnoPorId(Long id){
        Optional<Turno> turno = turnoRepository.findById(id);
        TurnoDTO turnoDTO = null;
        if(turno.isPresent()){
            turnoDTO = mapper.convertValue(turno, TurnoDTO.class);
        }
        return turnoDTO;
    }

    //4. Obtener un listado de todos los turnos registrados en la DB.
    public Set<TurnoDTO> buscarTodosTurnos(){
        List<Turno> turnos = turnoRepository.findAll();
        Set<TurnoDTO> turnosDTO = new HashSet<>();
        for (Turno turno : turnos) {
            turnosDTO.add(mapper.convertValue(turno, TurnoDTO.class));
        }
        return turnosDTO;
    }

    //5. Actualizar los datos de un turno.
    public Turno actualizarTurno(Turno turno){
        return guardarTurno(turno);
    }

    //6.Eliminar un turno por id.
    public void eliminarTurnoPorId(Long id){
        turnoRepository.deleteById(id);
    }

    //7.Eliminar todos los turnos de la base de datos
    public void eliminarTodosTurnos(){
        turnoRepository.deleteAll();
    }
}
