package com.example.dh.ClinicaOdontologica;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import org.apache.log4j.Logger;
import com.example.dh.ClinicaOdontologica.model.Odontologo;
import com.example.dh.ClinicaOdontologica.service.OdontologoService;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

public class OdontologoIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OdontologoService odontologoService;

    @Before // antes de cada tests, creo y registro odontólogos en la base de datos para que no esté vacía.
    public void crearOdontologos()throws BadRequestException{
        Odontologo odontologo = new Odontologo("Odontologo","De prueba",1111L);
        Odontologo odontologo2 = new Odontologo("Odontologo","De prueba2", 2222L);
        odontologoService.registrarOdontologo(odontologo);
        odontologoService.registrarOdontologo(odontologo2);
    };

    @Test
    public void buscarTodosOdontologosTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/todos")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarOdontologosPorIdTest() throws Exception {
        MvcResult respuesta = this.mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}","1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertEquals("application/json", respuesta.getResponse().getContentType());
    }
}
