package com.example.dh.ClinicaOdontologica.Controller;
import com.example.dh.ClinicaOdontologica.DTO.OdontologoDTO;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import java.util.Collection;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {


    private OdontologoService odontologoService;
    private static final Logger logger = Logger.getLogger(OdontologoController.class); //Instacia de logger para poder realizar logs.

    @Autowired
    //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //1.Registrar odontólogos en la base de datos.
    @PostMapping
    public ResponseEntity<?> registrarOdontologo(@RequestBody Odontologo odontologo){
        odontologoService.registrarOdontologo(odontologo);
        logger.info("El odontologo " + odontologo.getNombre() + " " + odontologo.getApellido() + " ha sido guardado correctamente en la base de datos");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //2.Buscar odontologos por id
    @GetMapping("/{id}")
    public OdontologoDTO buscarOdontologo(@PathVariable Long id){
        if(odontologoService.buscarOdontologoPorId(id)==null) {
            logger.info("No se encuentra en la base de datos el odontologo con id: " + id );
        }
        return odontologoService.buscarOdontologoPorId(id);
    }

    //3.Listar todos los odontólogos
    @GetMapping("/todos")
    public Collection<OdontologoDTO> listarTodosOdontologos(){
        if(odontologoService.buscarTodosOdontologos().isEmpty()) {
            logger.info("No se encuentran odontólogos en la base de datos.");
        }
        return odontologoService.buscarTodosOdontologos();
    }

    //4.Actualizar los datos de un odontólogo
    @PutMapping
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Odontologo odontologo){
       odontologoService.actualizarOdontologo(odontologo);
       logger.info("El odontologo " + odontologo.getNombre() + " " + odontologo.getApellido() + " ha sido actualizado correctamente en la base de datos");
       return ResponseEntity.ok(HttpStatus.OK);
    }

    //5.Eliminar odontologo por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id){
        if(odontologoService.buscarOdontologoPorId(id) != null) {
            odontologoService.eliminarOdontologoPorId(id);
            logger.info("El odontologo con id: " + id + " ha sido eliminado correctamente.");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //6.Eliminar todos los odontologos de la base de datos
    @DeleteMapping("/todos")
    public ResponseEntity<?> eliminarTodosOdontologos(){
        if(odontologoService.buscarTodosOdontologos() != null) {
            odontologoService.eliminarTodosOdontologos();
            logger.info("Todos los odontólogos han sido eliminados de la base de datos correctamente.");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
