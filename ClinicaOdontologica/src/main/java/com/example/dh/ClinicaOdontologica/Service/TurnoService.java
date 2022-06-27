package com.example.dh.ClinicaOdontologica.Service;

import com.example.dh.ClinicaOdontologica.Model.Turno;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {

    private IDaoRepository<Turno> turnoDao;
    @Autowired
    public TurnoService(IDaoRepository<Turno> turnoDao){
        this.turnoDao = turnoDao;
    }

    //métodos
    //1. Registrar un turno en la base de datos.
    public Turno registrarTurno(Turno turno) throws Exception{
        return turnoDao.agregar(turno);
    }
    //2. Buscar un turno por id.
    public Turno buscarTurnoPorId(Long id) throws Exception{
        return turnoDao.buscarPorId(id);
    }
    //3. Obtener un listado de todos los turnos registrados en la DB.
    public List<Turno> buscarTodosTurnos() throws Exception{
        return turnoDao.buscarTodos();
    }

    //4. Actualizar los datos de un turno.
    public Turno actualizarTurno(Turno turno) throws Exception{
        return turnoDao.actualizar(turno);
    }

    //5.Eliminar un turno por id.
    public void eliminarTurnoPorId(Long id) throws Exception{
        turnoDao.eliminarPorId(id);
    }

    //6.Eliminar todos los turnos de la base de datos
    public void eliminarTodosTurnos() throws Exception{
        turnoDao.eliminarTodos();
    }

}
