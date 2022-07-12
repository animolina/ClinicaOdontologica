package com.example.dh.ClinicaOdontologica;
import com.example.dh.ClinicaOdontologica.dto.TurnoDTO;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Domicilio;
import com.example.dh.ClinicaOdontologica.model.Odontologo;
import com.example.dh.ClinicaOdontologica.model.Paciente;
import com.example.dh.ClinicaOdontologica.model.Turno;
import com.example.dh.ClinicaOdontologica.service.OdontologoService;
import com.example.dh.ClinicaOdontologica.service.PacienteService;
import com.example.dh.ClinicaOdontologica.service.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //despues de cada test reinicia el contexto de la app (se recrea la base de datos)
public class TurnoServiceTest {

    private TurnoService turnoService;
    private OdontologoService odontologoService;
    private PacienteService pacienteService;

    @Autowired
    public TurnoServiceTest(TurnoService turnoService, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoService = turnoService;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
    }

    @BeforeEach
    // antes de cada tests, creo y registro pacientes y odontologos, para poder registrar turnos en la base de datos.
    public void crearTurnos() throws BadRequestException, EntityNotFoundException {
        Paciente paciente1 = new Paciente("Paciente", "De prueba1", new Domicilio("calle", 1111, "Localidad1", "Provincia1"));
        Paciente paciente2 = new Paciente("Paciente", "De prueba2", new Domicilio("calle", 2222, "Localidad2", "Provincia2"));
        pacienteService.registrarPaciente(paciente1);
        pacienteService.registrarPaciente(paciente2);
        Odontologo odontologo1 = new Odontologo("Odontologo", "De prueba", 1111L);
        Odontologo odontologo2 = new Odontologo("Odontologo", "De prueba2", 2222L);
        odontologoService.registrarOdontologo(odontologo1);
        odontologoService.registrarOdontologo(odontologo2);
        Turno turno1 = new Turno(paciente1, odontologo1);
        Turno turno2 = new Turno(paciente2, odontologo2);
        turnoService.registrarTurno(turno1);
        turnoService.registrarTurno(turno2);
    }

    @Test //testeo registrar un turno en la base de datos
    public void testRegistrarTurno() throws BadRequestException, EntityNotFoundException {
        Paciente pacienteTurno =new Paciente("Paciente", "De prueba3", new Domicilio("calle", 3333, "Localidad3", "Provincia3"));
        Odontologo odontologoTurno = new Odontologo("Odontologo", "De prueba3", 3333L);
        pacienteService.registrarPaciente(pacienteTurno); //registro el paciente para el turno(debe existir en la BD para poder asignar turnos)
        odontologoService.registrarOdontologo(odontologoTurno); //registro el odontologo para el turno.(debe existir en la BD para poder asignar turnos)
        Turno turno = new Turno(pacienteTurno,odontologoTurno);
        turnoService.registrarTurno(turno); //registro el turno.
        TurnoDTO turnoDTO = turnoService.buscarTurnoPorId(turno.getId());
        assertTrue(turnoDTO != null); //compruebo al buscar por id, con el id del turno creado, que el mismo no sea nulo.
    }

    @Test //testeo listar todos los turnos
    public void testListarTurnos() throws EntityNotFoundException {
        Set<TurnoDTO> turnos = turnoService.buscarTodosTurnos();
        assertFalse(turnos.isEmpty()); //Compruebo que la lista de turnos no esté vacía.
    }

    @Test //testeo buscar un turno por id.
    public void testBuscarTurnoPorId() throws EntityNotFoundException {
        assertNotNull(turnoService.buscarTurnoPorId(1L)); // Compruebo que no sea nulo.
    }

    @Test //testeo actualizar un turno
    public void testActualizarUnTurno() throws EntityNotFoundException, BadRequestException {
        Paciente pacienteActualizado =new Paciente(2L,"Actualizado", "Actualizado", new Domicilio(2L, "actualizada", 2222));
        Odontologo odontologoActualizado = new Odontologo(2L,"Actualizado", "Actualizado", 2222L);
        Turno turnoActualizado = new Turno(2L,pacienteActualizado,odontologoActualizado);
        turnoService.actualizarTurno(turnoActualizado);
        assertTrue(turnoService.buscarTurnoPorId(2L) != null); //Compruebo que el turno actualizado, no sea null.
    }

    @Test //testeo eliminar un turno.
    public void testEliminarTurnoPorId() throws EntityNotFoundException {
        turnoService.eliminarTurnoPorId(1L);
        assertThrows(EntityNotFoundException.class, () -> {
            turnoService.buscarTurnoPorId(1L);//Compruebo que arroja correctamente la excepcion EntityNotFoundException
        });
    }

    @Test //testeo eliminar todos los turnos de la base de datos
    public void testEliminarTurnosTodos() throws EntityNotFoundException {
        turnoService.eliminarTodosTurnos();
        assertThrows(EntityNotFoundException.class, () -> {
            turnoService.buscarTodosTurnos();//Compruebo que arroja correctamente la excepcion EntityNotFoundException
        });
    }
}
