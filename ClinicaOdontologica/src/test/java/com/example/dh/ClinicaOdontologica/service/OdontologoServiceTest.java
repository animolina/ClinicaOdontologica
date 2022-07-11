package com.example.dh.ClinicaOdontologica.service;
import com.example.dh.ClinicaOdontologica.dto.OdontologoDTO;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Odontologo;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OdontologoServiceTest {

   private OdontologoService odontologoService;

    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    OdontologoServiceTest(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @BeforeClass //creo un odontólogo antes de correr los tests.
    public Odontologo crearOdontologo(){
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Odontologo");
        odontologo.setApellido("De prueba");
        odontologo.setMatricula(25854L);
        return odontologo;
    };

    @Test //testeo registrar un odontólogo en la base de datos y posteriormente eliminarlo.
    public void testRegistrarOdontologoyEliminarlo() throws BadRequestException, EntityNotFoundException {
        Odontologo odontologoCreado = crearOdontologo();
        odontologoService.registrarOdontologo(odontologoCreado);
        OdontologoDTO odontologoELena = odontologoService.buscarOdontologoPorId(odontologoCreado.getId());
        assertTrue(odontologoELena != null); //compruebo al buscar por id, con el id del odontólogo creado, si el mismo se registró correctamente.
        odontologoService.eliminarOdontologoPorId(odontologoELena.getId());//elimino el odontologo para que no quede data de prueba en la base de datos.
    }

    @Test //testeo listar todos los odontólogos.
    public void testListarOdontologos() throws EntityNotFoundException{
        List<OdontologoDTO> odontologos = odontologoService.buscarTodosOdontologos();
        assertTrue(!odontologos.isEmpty()); //Compruebo que la lista de odontólogos no esté vacía.
    }


}
