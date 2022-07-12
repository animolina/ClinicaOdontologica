package com.example.dh.ClinicaOdontologica;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Domicilio;
import com.example.dh.ClinicaOdontologica.model.Odontologo;
import com.example.dh.ClinicaOdontologica.model.Paciente;
import com.example.dh.ClinicaOdontologica.model.Turno;
import com.example.dh.ClinicaOdontologica.service.OdontologoService;
import com.example.dh.ClinicaOdontologica.service.PacienteService;
import com.example.dh.ClinicaOdontologica.service.TurnoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //despues de cada test reinicia el contexto de la app (se recrea la base de datos)
@AutoConfigureMockMvc
public class TurnoIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @Before // antes de cada tests, creo y registro pacientes y odontologos, para poder registrar turnos en la base de datos.
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

    @Test
    public void buscarTodosTurnosTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/turnos/todos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarTurnosPorIdTest() throws Exception {
        MvcResult respuesta = this.mockMvc.perform(MockMvcRequestBuilders.get("/turnos/{id}","1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertEquals("application/json", respuesta.getResponse().getContentType());
    }
}
