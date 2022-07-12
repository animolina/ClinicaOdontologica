package com.example.dh.ClinicaOdontologica;
import com.example.dh.ClinicaOdontologica.dto.PacienteDTO;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Domicilio;
import com.example.dh.ClinicaOdontologica.model.Paciente;
import com.example.dh.ClinicaOdontologica.service.PacienteService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //despues de cada test reinicia el contexto de la app (se recrea la base de datos)
class PacienteServiceTest {

    private PacienteService pacienteService;

    @Autowired
        //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    PacienteServiceTest(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @BeforeEach// antes de cada tests, creo y registro pacientes en la base de datos para que no esté vacía.
    public void crearPacientes()throws BadRequestException{
        Paciente paciente1 = new Paciente("Paciente","De prueba1",new Domicilio("calle",1111,"Localidad1","Provincia1"));
        Paciente paciente2 = new Paciente("Paciente","De prueba2",new Domicilio("calle",2222,"Localidad2","Provincia2"));
        pacienteService.registrarPaciente(paciente1);
        pacienteService.registrarPaciente(paciente2);
    };

    @Test //testeo registrar un paciente en la base de datos
    public void testRegistrarPaciente() throws BadRequestException, EntityNotFoundException {
        Paciente paciente = new Paciente ("Paciente","De prueba3",new Domicilio("calle",3333,"Localidad3","Provincia3"));
        pacienteService.registrarPaciente(paciente);
       PacienteDTO pacienteDTO = pacienteService.buscarPacientePorId(paciente.getId());
        assertTrue(pacienteDTO != null); //compruebo al buscar por id, con el id del paciente creado, que el mismo no sea nulo.

    }
    @Test //testeo listar todos los pacientes.
    public void testListarPacientes() throws EntityNotFoundException {
        Set<PacienteDTO> pacientes = pacienteService.buscarTodosPacientes();
        assertFalse(pacientes.isEmpty()); //Compruebo que la lista de pacientes no esté vacía.
    }

    @Test //testeo buscar un paciente por id.
    public void testBuscarPacientePorId() throws EntityNotFoundException{
        assertNotNull(pacienteService.buscarPacientePorId(1L)); // Compruebo que no sea nulo.
    }

    @Test //testeo actualizar un paciente
    public void testActualizarUnPaciente() throws EntityNotFoundException, BadRequestException{
        Paciente pacienteActualizado = new Paciente(1L, "Paciente","Actualizado",new Domicilio(1L,"Calle",1111));
        pacienteService.actualizarPaciente(pacienteActualizado);
        assertTrue(pacienteService.buscarPacientePorId(1L).getApellido() == "Actualizado"); //Compruebo que se haya actualizado, viendo el apellido actual.
    }

    @Test //testeo eliminar un paciente.
    public void testEliminarPacientePorId()throws EntityNotFoundException{
        pacienteService.eliminarPacientePorId(1L);
        assertThrows(EntityNotFoundException.class, () -> {
            pacienteService.buscarPacientePorId(1L);//Compruebo que arroja correctamente la excepcion EntityNotFoundException
        });
    }

    @Test //testeo eliminar todos los pacientes de la base de datos
    public void testEliminarPacientesTodos()throws EntityNotFoundException{
        pacienteService.eliminarTodosPacientes();
        assertThrows(EntityNotFoundException.class, () -> {
            pacienteService.buscarTodosPacientes();//Compruebo que arroja correctamente la excepcion EntityNotFoundException
        });
    }

}
