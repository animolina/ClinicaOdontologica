package com.example.dh.ClinicaOdontologica.Controller;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //1.Agregar pacientes
    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) throws Exception {
        return ResponseEntity.ok(pacienteService.registrarPaciente(paciente));
    }

    //2.Buscar pacientes por id
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(pacienteService.buscarPacientePorId(id));
    }

    //3.Listar todos los pacientes
    @GetMapping("/todos")
    public ResponseEntity<List<Paciente>> listarTodosPacientes() throws Exception {
        return ResponseEntity.ok(pacienteService.buscarTodosPacientes());
    }

    //4.Actualizar los datos de un paciente
    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws Exception {
        ResponseEntity<Paciente> response = null;
        if (paciente.getId() != null && pacienteService.buscarPacientePorId(paciente.getId()) != null) {
            response = ResponseEntity.ok(pacienteService.actualizarPaciente(paciente));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //5.Eliminar paciente por id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws Exception {
        ResponseEntity<String> response = null;
        if (pacienteService.buscarPacientePorId(id) != null) {
            pacienteService.eliminarPacientePorId(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //6.Eliminar todos los pacientes de la base de datos
    @DeleteMapping("/eliminarTodos")
    public ResponseEntity<String> eliminarTodosPacientes() throws Exception{
        ResponseEntity<String> response = null;
        if(pacienteService.buscarTodosPacientes()!=null){
            pacienteService.eliminarTodosPacientes();
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminados");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }




}
