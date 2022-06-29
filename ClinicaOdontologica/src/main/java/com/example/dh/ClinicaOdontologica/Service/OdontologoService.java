package com.example.dh.ClinicaOdontologica.Service;
import com.example.dh.ClinicaOdontologica.DTO.OdontologoDTO;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Repository.IOdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OdontologoService {

    private IOdontologoRepository odontologoRepository;
    ObjectMapper mapper; //necesario para la conversión de la entidad a DTO y viceversa.

    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public OdontologoService(IOdontologoRepository odontologoRepository, ObjectMapper mapper) {
        this.odontologoRepository = odontologoRepository;
        this.mapper = mapper;
    }

    //métodos
    //1. Guardar un odontólogo en la base de datos.
    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }

    //2. Registrar un odontólogo en la base de datos
    public Odontologo registrarOdontologo(Odontologo odontologo){
        return guardarOdontologo(odontologo);
    }

    //3. Buscar un odontólogo por id.
    public OdontologoDTO buscarOdontologoPorId(Long id){
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        OdontologoDTO odontologoDTO = null;
        if(odontologo.isPresent()){
            odontologoDTO = mapper.convertValue(odontologo, OdontologoDTO.class);
        }
        return odontologoDTO;
    }

    //4. Obtener un listado de todos los odontólogos registrados en la DB.
    public Set<OdontologoDTO> buscarTodosOdontologos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        Set<OdontologoDTO> odontologosDTO = new HashSet<>();
        for (Odontologo odontologo:odontologos) {
            odontologosDTO.add(mapper.convertValue(odontologo, OdontologoDTO.class));
        }
        return odontologosDTO;
    }

    //5. Actualizar los datos de un odontólogo.
    public Odontologo actualizarOdontologo(Odontologo odontologo){
       return guardarOdontologo(odontologo);
    }

    //6.Eliminar un odontólogo por id.
    public void eliminarOdontologoPorId(Long id){
        odontologoRepository.deleteById(id);
    }

    //7.Eliminar todos los odontologos de la base de datos
    public void eliminarTodosOdontologos(){
        odontologoRepository.deleteAll();
    }

}
