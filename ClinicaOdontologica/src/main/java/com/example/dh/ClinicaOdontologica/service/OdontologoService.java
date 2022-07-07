package com.example.dh.ClinicaOdontologica.service;
import com.example.dh.ClinicaOdontologica.dto.OdontologoDTO;
import com.example.dh.ClinicaOdontologica.exception.BadRequestException;
import com.example.dh.ClinicaOdontologica.exception.EntityNotFoundException;
import com.example.dh.ClinicaOdontologica.model.Odontologo;
import com.example.dh.ClinicaOdontologica.repository.IOdontologoRepository;
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
    public Odontologo guardarOdontologo(Odontologo odontologo) throws BadRequestException {
       if(odontologo == null ||odontologo.getNombre() == null || odontologo.getApellido() == null || odontologo.getMatricula() == null){
           throw new BadRequestException("Los datos ingresados para registrar el odontólogo no son correctos o están incompletos. Debe incluir obligatoriamente: nombre, apellido y matricula del odontólogo.");
       } else {
           return odontologoRepository.save(odontologo);
       }
    }

    //2. Registrar un odontólogo en la base de datos
    public Odontologo registrarOdontologo(Odontologo odontologo) throws BadRequestException{
        return guardarOdontologo(odontologo);
    }

    //3. Buscar un odontólogo por id.
    public OdontologoDTO buscarOdontologoPorId(Long id) throws EntityNotFoundException{
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        OdontologoDTO odontologoDTO = null;
        if(odontologo.isEmpty()){
            throw new EntityNotFoundException("El odontologo con el id: " + id + " no se encuentra en la base de datos");
        } else{
            odontologoDTO = mapper.convertValue(odontologo, OdontologoDTO.class);
        }

        return odontologoDTO;
    }

    //4. Obtener un listado de todos los odontólogos registrados en la DB.
    public Set<OdontologoDTO> buscarTodosOdontologos() throws EntityNotFoundException {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        Set<OdontologoDTO> odontologosDTO = new HashSet<>();
        if(odontologos.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran odontólogos en la base de datos");
        } else {
            for (Odontologo odontologo : odontologos) {
                odontologosDTO.add(mapper.convertValue(odontologo, OdontologoDTO.class));
            }
        }
        return odontologosDTO;
    }

    //5. Actualizar los datos de un odontólogo.
    public Odontologo actualizarOdontologo(Odontologo odontologo) throws EntityNotFoundException, BadRequestException{
        buscarOdontologoPorId(odontologo.getId()); OdontologoService odontologoService;
   PacienteService pacienteService;

        return guardarOdontologo(odontologo);
    }

    //6.Eliminar un odontólogo por id.
    public void eliminarOdontologoPorId(Long id) throws EntityNotFoundException{
        buscarOdontologoPorId(id);
        odontologoRepository.deleteById(id);
    }

    //7.Eliminar todos los odontologos de la base de datos
    public void eliminarTodosOdontologos() throws EntityNotFoundException{
        buscarTodosOdontologos();
        odontologoRepository.deleteAll();
    }

}
