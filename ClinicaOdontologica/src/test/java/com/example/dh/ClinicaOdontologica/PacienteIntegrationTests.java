package com.example.dh.ClinicaOdontologica;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.model.Domicilio;
import com.example.dh.ClinicaOdontologica.model.Paciente;
import com.example.dh.ClinicaOdontologica.service.PacienteService;
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
public class PacienteIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacienteService pacienteService;

    @Before // antes de cada tests, creo y registro pacientes en la base de datos para que no esté vacía.
    public void crearPacientes()throws BadRequestException{
        Paciente paciente1 = new Paciente("Paciente","De prueba1",new Domicilio("calle",1111,"Localidad1","Provincia1"));
        Paciente paciente2 = new Paciente("Paciente","De prueba2",new Domicilio("calle",2222,"Localidad2","Provincia2"));
        pacienteService.registrarPaciente(paciente1);
        pacienteService.registrarPaciente(paciente2);
    };

    @Test
    public void buscarTodosPacientesTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/todos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarPacientesPorIdTest() throws Exception {
        MvcResult respuesta = this.mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}","1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertEquals("application/json", respuesta.getResponse().getContentType());
    }
}
