package com.example.dh.ClinicaOdontologica;
import com.example.dh.ClinicaOdontologica.dto.OdontologoDTO;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Odontologo;
import com.example.dh.ClinicaOdontologica.service.OdontologoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //despues de cada test reinicia el contexto de la app (se recrea la base de datos)
class OdontologoServiceTest {

   private OdontologoService odontologoService;

    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    OdontologoServiceTest(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @BeforeEach// antes de cada tests, creo y registro odontólogos en la base de datos para que no esté vacía.
    public void crearOdontologos()throws BadRequestException,EntityNotFoundException{
        Odontologo odontologo = new Odontologo("Odontologo","De prueba",1111L);
        Odontologo odontologo2 = new Odontologo("Odontologo","De prueba2", 2222L);
        odontologoService.registrarOdontologo(odontologo);
        odontologoService.registrarOdontologo(odontologo2);
    };

    @Test //testeo registrar un odontólogo en la base de datos
    public void testRegistrarOdontologo() throws BadRequestException, EntityNotFoundException {
        Odontologo odontologo = new Odontologo("Odontologo","De prueba3", 3333L);
        odontologoService.registrarOdontologo(odontologo);
        OdontologoDTO odontologoDTO = odontologoService.buscarOdontologoPorId(odontologo.getId());
        assertTrue(odontologoDTO != null); //compruebo al buscar por id, con el id del odontólogo creado, que el mismo no sea nulo.

    }
    @Test //testeo listar todos los odontólogos.
    public void testListarOdontologos() throws EntityNotFoundException {
        List<OdontologoDTO> odontologos = odontologoService.buscarTodosOdontologos();
        assertFalse(odontologos.isEmpty()); //Compruebo que la lista de odontólogos no esté vacía,
    }

    @Test //testeo buscar un odontólogo por id.
    public void testBuscarOdontologoPorId() throws EntityNotFoundException{
      assertNotNull(odontologoService.buscarOdontologoPorId(1L)); // Compruebo que no sea nulo.
    }
    
    @Test //testeo actualizar un odontólogo
    public void testActualizarUnOdontologo() throws EntityNotFoundException, BadRequestException{
        Odontologo odontologoActualizado = new Odontologo(1L,"Odontologo","Actualizado", 1111L);
        odontologoService.actualizarOdontologo(odontologoActualizado);
        assertTrue(odontologoService.buscarOdontologoPorId(1L).getApellido() == "Actualizado"); //Compruebo que se haya actualizado, viendo el apellido actual.
    }

    @Test //testeo eliminar un odontólogo.
    public void testEliminarOdontologoPorId()throws EntityNotFoundException{
        odontologoService.eliminarOdontologoPorId(1L);
        assertThrows(EntityNotFoundException.class, () -> {
            odontologoService.buscarOdontologoPorId(1L);//Compruebo que arroja correctamente la excepcion EntityNotFoundException
        });
    }
    
    @Test //testeo eliminar todos los odontólogos de la base de datos
    public void testEliminarOdontologoTodos()throws EntityNotFoundException{
        odontologoService.eliminarTodosOdontologos();
        assertThrows(EntityNotFoundException.class, () -> {
            odontologoService.buscarTodosOdontologos();//Compruebo que arroja correctamente la excepcion EntityNotFoundException
    });
    }

}
