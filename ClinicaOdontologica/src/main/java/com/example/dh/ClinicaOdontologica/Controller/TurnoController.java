package com.example.dh.ClinicaOdontologica.Controller;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Model.Turno;
import com.example.dh.ClinicaOdontologica.Repository.Impl.OdontologoDaoH2;
import com.example.dh.ClinicaOdontologica.Repository.Impl.PacienteDaoH2;
import com.example.dh.ClinicaOdontologica.Repository.Impl.TurnoDaoH2;
import com.example.dh.ClinicaOdontologica.Service.OdontologoService;
import com.example.dh.ClinicaOdontologica.Service.PacienteService;
import com.example.dh.ClinicaOdontologica.Service.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService = new TurnoService(new TurnoDaoH2());
    private PacienteService pacienteService = new PacienteService(new PacienteDaoH2());
    private OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());

    //1.Agregar turnos
    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) throws Exception {
        ResponseEntity<Turno> response = null;
        if (pacienteService.buscarPacientePorId(turno.getPaciente().getId()) != null && odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId())!= null) {
            response = ResponseEntity.ok(turnoService.registrarTurno(turno));
        } else{
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
