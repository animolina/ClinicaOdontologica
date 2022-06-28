package com.example.dh.ClinicaOdontologica.Controller;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/odontologos")
public class OdontologoController {


    private OdontologoService odontologoService;


    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //1.Agregar odontólogos
    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) throws Exception {
        return ResponseEntity.ok(odontologoService.registrarOdontologo(odontologo));
    }

    //2.Buscar odontologos por id
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(odontologoService.buscarOdontologoPorId(id));
    }

    //3.Listar todos los odontólogos
    @GetMapping("/todos")
    public ResponseEntity<List<Odontologo>> listarTodosOdontologos() throws Exception {
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    //4.Actualizar los datos de un odontólogo
    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws Exception {
        ResponseEntity<Odontologo> response = null;
        if (odontologo.getId() != null && odontologoService.buscarOdontologoPorId(odontologo.getId()) != null) {
            response = ResponseEntity.ok(odontologoService.actualizarOdontologo(odontologo));
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //5.Eliminar odontologo por id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws Exception {
        ResponseEntity<String> response = null;
        if (odontologoService.buscarOdontologoPorId(id) != null) {
            odontologoService.eliminarOdontologoPorId(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    //6.Eliminar todos los odontologos de la base de datos
    @DeleteMapping("/eliminarTodos")
    public ResponseEntity<String> eliminarTodosOdontologos() throws Exception{
        ResponseEntity<String> response = null;
        if(odontologoService.buscarTodosOdontologos()!=null){
            odontologoService.eliminarTodosOdontologos();
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminados");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

}
