package com.example.dh.ClinicaOdontologica.Controller;
import com.example.dh.ClinicaOdontologica.DTO.PacienteDTO;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Service.PacienteService;
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
    public ResponseEntity<?> registrarPaciente(@RequestBody Paciente paciente){
        pacienteService.registrarPaciente(paciente);
        logger.info("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " ha sido guardado correctamente en la base de datos");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //2.Buscar pacientes por id
    @GetMapping("/{id}")
    public PacienteDTO buscarPaciente(@PathVariable Long id){
        return pacienteService.buscarPacientePorId(id);
    }

    //3.Listar todos los pacientes
    @GetMapping("/todos")
    public Collection<PacienteDTO> listarTodosPacientes(){
        return pacienteService.buscarTodosPacientes();
    }

    //4.Actualizar los datos de un paciente
    @PutMapping
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente paciente){
        pacienteService.actualizarPaciente(paciente);
        logger.info("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " ha sido actualizado correctamente en la base de datos");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //5.Eliminar paciente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id){
        if(pacienteService.buscarPacientePorId(id) != null) {
            pacienteService.eliminarPacientePorId(id);
            logger.info("El paciente con id: " + id + " ha sido eliminado correctamente.");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //6.Eliminar todos los pacientes de la base de datos
    @DeleteMapping("/todos")
    public ResponseEntity<?> eliminarTodosPacientes(){
        if(pacienteService.buscarTodosPacientes() != null) {
            pacienteService.eliminarTodosPacientes();
            logger.info("Todos los pacientes han sido eliminados de la base de datos correctamente.");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
