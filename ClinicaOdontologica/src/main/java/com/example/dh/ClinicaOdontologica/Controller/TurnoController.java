package com.example.dh.ClinicaOdontologica.Controller;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Model.Turno;
import com.example.dh.ClinicaOdontologica.Service.OdontologoService;
import com.example.dh.ClinicaOdontologica.Service.PacienteService;
import com.example.dh.ClinicaOdontologica.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;
    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    //1.Agregar turnos
    @PostMapping
    public ResponseEntity registrarTurno(@RequestBody Turno turno) throws Exception {
        ResponseEntity response = null;
        Odontologo odontologoEncontrado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(turno.getPaciente().getId());

        if(odontologoEncontrado == null){
            response = new ResponseEntity<String>("No se encuentra el odontologo en la base de datos",HttpStatus.BAD_REQUEST);
        }

        if(pacienteEncontrado == null){
            response = new ResponseEntity<String>("No se encuentra el paciente en la base de datos",HttpStatus.BAD_REQUEST);

        }
        else if(odontologoEncontrado != null && pacienteEncontrado != null){
            response = ResponseEntity.ok(turnoService.registrarTurno(turno));
        }
        return response;
    }

    //2.Buscar turnos por id
    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(turnoService.buscarTurnoPorId(id));
    }

    //3.Listar todos los turnos
    @GetMapping("/todos")
    public ResponseEntity<List<Turno>> listarTodosTurnos() throws Exception {
        return ResponseEntity.ok(turnoService.buscarTodosTurnos());
    }

    //4.Actualizar los datos de un turno
    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) throws Exception {
        ResponseEntity<Turno> response = null;
        if (turno.getId() != null && turnoService.buscarTurnoPorId(turno.getId()) != null) {
            response = ResponseEntity.ok(turnoService.actualizarTurno(turno));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //5.Eliminar turno por id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws Exception {
        ResponseEntity<String> response = null;
        if (turnoService.buscarTurnoPorId(id) != null) {
            turnoService.eliminarTurnoPorId(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //6.Eliminar todos los turnos de la base de datos
    @DeleteMapping("/eliminarTodos")
    public ResponseEntity<String> eliminarTodosTurnos() throws Exception{
        ResponseEntity<String> response = null;
        if(turnoService.buscarTodosTurnos()!=null){
            turnoService.eliminarTodosTurnos();
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminados");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }
}
