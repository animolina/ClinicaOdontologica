package com.example.dh.ClinicaOdontologica.Controller;
import com.example.dh.ClinicaOdontologica.DTO.TurnoDTO;
import com.example.dh.ClinicaOdontologica.Model.Turno;
import com.example.dh.ClinicaOdontologica.Service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private static final Logger logger = Logger.getLogger(TurnoController.class); //Instacia de logger para poder realizar logs.
    @Autowired
    //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }
    //1.Agregar turnos
    @PostMapping
    public ResponseEntity<?> registrarTurno(@RequestBody Turno turno){
        turnoService.registrarTurno(turno);
        logger.info("El turno con el odontólogo " + turno.getOdontologo().getNombre() + " " + turno.getOdontologo().getApellido() + " en la fecha: " + turno.getFechaYhora() + " ha sido registrado correctamente en la base de datos");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //2.Buscar turnos por id
    @GetMapping("/{id}")
    public TurnoDTO buscarTurno (@PathVariable Long id){
        return turnoService.buscarTurnoPorId(id);
    }

    //3.Listar todos los turnos
    @GetMapping("/todos")
    public Collection<TurnoDTO> listarTodosTurnos(){
        return turnoService.buscarTodosTurnos();
    }

    //4.Actualizar los datos de un turno
    @PutMapping
    public ResponseEntity<?> actualizarTurno(@RequestBody Turno turno){
        turnoService.actualizarTurno(turno);
        logger.info("El turno con el id:  " + turno.getId() + " ha sido actualizado correctamente en la base de datos");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //5.Eliminar turno por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id){
        if(turnoService.buscarTurnoPorId(id) != null) {
            turnoService.eliminarTurnoPorId(id);
            logger.info("El turno con id: " + id + " ha sido eliminado correctamente.");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //6.Eliminar todos los turnos de la base de datos
    @DeleteMapping("/todos")
    public ResponseEntity<?> eliminarTodosTurnos(){
        if(turnoService.buscarTodosTurnos() != null) {
            turnoService.eliminarTodosTurnos();
            logger.info("Todos los turnos han sido eliminados de la base de datos correctamente.");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
