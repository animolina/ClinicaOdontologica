package com.example.dh.ClinicaOdontologica.controller;
import com.example.dh.ClinicaOdontologica.dto.PacienteDTO;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Paciente;
import com.example.dh.ClinicaOdontologica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;


@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService;
    private static final Logger logger = Logger.getLogger(PacienteController.class); //Instacia de logger para poder realizar logs.
    @Autowired
    //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    //1.Agregar pacientes
    @PostMapping
    public ResponseEntity<?> registrarPaciente(@RequestBody Paciente paciente)throws BadRequestException {
        pacienteService.registrarPaciente(paciente);
        logger.info("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " ha sido guardado correctamente en la base de datos");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //2.Buscar pacientes por id
    @GetMapping("/{id}")
    public PacienteDTO buscarPaciente(@PathVariable Long id)throws EntityNotFoundException {
        return pacienteService.buscarPacientePorId(id);
    }

    //3.Listar todos los pacientes
    @GetMapping("/todos")
    public Collection<PacienteDTO> listarTodosPacientes() throws EntityNotFoundException{
       return pacienteService.buscarTodosPacientes();
    }

    //4.Actualizar los datos de un paciente
    @PutMapping //Aclaración importante: en el body incluir id de paciente y id de domicilio.
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente paciente) throws EntityNotFoundException,BadRequestException{
        pacienteService.actualizarPaciente(paciente);
        logger.info("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " ha sido actualizado correctamente en la base de datos");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //5.Eliminar paciente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id)throws EntityNotFoundException{
       pacienteService.eliminarPacientePorId(id);
       logger.info("El paciente con id: " + id + " ha sido eliminado correctamente.");
       return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //6.Eliminar todos los pacientes de la base de datos
    @DeleteMapping("/todos")
    public ResponseEntity<?> eliminarTodosPacientes() throws EntityNotFoundException{
        pacienteService.eliminarTodosPacientes();
        logger.info("Todos los pacientes han sido eliminados de la base de datos correctamente.");
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
