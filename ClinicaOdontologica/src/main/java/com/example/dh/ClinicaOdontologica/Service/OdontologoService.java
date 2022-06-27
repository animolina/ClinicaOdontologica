package com.example.dh.ClinicaOdontologica.Service;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService {

    private IDaoRepository<Odontologo> odontologoDao;
    @Autowired
    public OdontologoService(IDaoRepository<Odontologo> odontologoDao){
        this.odontologoDao = odontologoDao;
    }

    //métodos
    //1. Registrar un odontólogo en la base de datos.
    public Odontologo registrarOdontologo(Odontologo odontologo) throws Exception {
        return odontologoDao.agregar(odontologo);
    }

    //2. Buscar un odontólogo por id.
    public Odontologo buscarOdontologoPorId(Long id) throws Exception{
        return odontologoDao.buscarPorId(id);
    }

    //3. Obtener un listado de todos los odontólogos registrados en la DB.
    public List<Odontologo> buscarTodosOdontologos() throws Exception{
        return odontologoDao.buscarTodos();
    }

    //4. Actualizar los datos de un odontólogo.
    public Odontologo actualizarOdontologo(Odontologo odontologo) throws Exception{
        return odontologoDao.actualizar(odontologo);
    }

    //5.Eliminar un odontólogo por id.
    public void eliminarOdontologoPorId(Long id) throws Exception{
        odontologoDao.eliminarPorId(id);
    }

    //6.Eliminar todos los odontologos de la base de datos
    public void eliminarTodosOdontologos() throws Exception{
        odontologoDao.eliminarTodos();
    }

}
