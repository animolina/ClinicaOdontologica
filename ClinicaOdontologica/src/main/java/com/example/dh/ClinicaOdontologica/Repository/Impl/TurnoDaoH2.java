package com.example.dh.ClinicaOdontologica.Repository.Impl;
import com.example.dh.ClinicaOdontologica.Model.Domicilio;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Model.Turno;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import com.example.dh.ClinicaOdontologica.Util.Util;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class TurnoDaoH2 implements IDaoRepository<Turno> {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/Integrador;INIT=RUNSCRIPT FROM 'create.sql'"; //con esta instrucción cuando se conecta a la base ejecuta el script de sql que esta en el archivo create.sql
    private final static String DB_USER = "sa";
    private final static String DB_PASSWORD = "";
    private PacienteDaoH2 pacienteDaoH2 = new PacienteDaoH2();
    private OdontologoDaoH2 odontologoDaoH2 = new OdontologoDaoH2();
    private static final Logger logger = Logger.getLogger(TurnoDaoH2.class); //Objeto logger para empleo de logs.
    @Override
    public Turno agregar(Turno turno) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.2 Creo una sentencia
            preparedStatement = connection.prepareStatement("INSERT INTO turnos (paciente_id, odontologo_id, fecha_y_hora) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, turno.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setLong(1,turno.getPaciente().getId());
                preparedStatement.setLong(2, turno.getOdontologo().getId());
                //Hay que convertir el LocalDate en sql.LocalDate (son clases distintas)
                preparedStatement.setDate(3, Util.utilDateToSqlDate(turno.getFechaYhora()));
                preparedStatement.executeUpdate();
                ResultSet keys = preparedStatement.getGeneratedKeys();
                if(keys.next())
                    turno.setId(keys.getLong(1));
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al guardar el turno.", e);
            throw e;
        }
        logger.info("El turno con el id: " + turno.getId() + " para el paciente:  " + turno.getPaciente() + " con el odontologo:  " + turno.getOdontologo() + " ha sido agendado correctamente en la base de datos");
        return turno;
    }

    @Override
    public Turno buscarPorId(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Turno turno = null;
        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM turnos where id = ?");

            try {
                //3. Seteo atributos y ejecuto una sentencia SQL
                preparedStatement.setLong(1, id);
                ResultSet result = preparedStatement.executeQuery();
                //4 Obtener resultados
                while (result.next()) {
                    Long idTurno = result.getLong("id");
                    Long idPaciente = result.getLong("paciente_id");
                    Long idOdontologo = result.getLong("odontologo_id");
                    java.sql.Date fechaYhora = result.getDate("fecha_y_hora");
                    Paciente paciente = pacienteDaoH2.buscarPorId(idPaciente);
                    Odontologo odontologo = odontologoDaoH2.buscarPorId(idOdontologo);
                    turno = new Turno(idTurno,paciente,odontologo,fechaYhora);

                }
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("No se encuentra el turno con el id solicitado.", e);
            throw e;
        }
        logger.info("El turno con id: " + turno.getId() + " ha sido encontrado en la base de datos");
        return turno;
    }

    @Override
    public List<Turno> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Turno> turnos = new ArrayList<>(); //creamos listado vacio de turnos.
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT *  FROM turnos");

            try {
                //3 Ejecutar una sentencia SQL
                ResultSet result = preparedStatement.executeQuery();

                //4 Obtener resultados
                while (result.next()) {
                    Long idTurno = result.getLong("id");
                    Long idPaciente = result.getLong("paciente_id");
                    Long idOdontologo = result.getLong("odontologo_id");
                    java.sql.Date fechaYhora = result.getDate("fecha_y_hora");
                    Paciente paciente = pacienteDaoH2.buscarPorId(idPaciente);
                    Odontologo odontologo = odontologoDaoH2.buscarPorId(idOdontologo);
                    Turno turno = new Turno(idTurno,paciente,odontologo,fechaYhora);
                    turnos.add(turno); //agregamos el turno al listado.
                }

            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar mostrar todos los turnos registrados.", e);
            throw e;
        }
        logger.info("Se han encontrado turnos en la base de datos: " + turnos);
        return turnos;
    }

    @Override
    public Turno actualizar(Turno turno) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            //2.1 Actualizo el paciente y odontologo
            Paciente paciente = pacienteDaoH2.actualizar(turno.getPaciente());
            Odontologo odontologo = odontologoDaoH2.actualizar(turno.getOdontologo());
            //2.2Creo una sentencia
            preparedStatement = connection.prepareStatement("UPDATE turnos SET paciente_id = ?, odontologo_id = ?, fecha_y_hora = ? WHERE id = ?");
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, turno.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setLong(1,paciente.getId());
                preparedStatement.setLong(2,odontologo.getId());
                preparedStatement.setDate(3,Util.utilDateToSqlDate(turno.getFechaYhora()));
                preparedStatement.setLong(4, turno.getId());
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar actualizar el turno.", e);
            throw e;
        }
        logger.info("El turno con id:  " + turno.getId() + " ha sido actualizado correctamente en la base de datos");
        return turno;
    }

    @Override
    public void eliminarPorId(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("DELETE FROM turnos where id = ?");

            try {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar el turno con el id solicitado.", e);
            throw e;
        }
        logger.info("El turno con id: " + id + " ha sido eliminado correctamente.");

    }

    @Override
    public void eliminarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("DELETE FROM turnos");

            try {
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar todos los turnos.", e);
            throw e;
        }
        logger.info("Todos los turnos han sido eliminado correctamente.");

    }
}
