package com.example.dh.ClinicaOdontologica.Repository.Impl;
import com.example.dh.ClinicaOdontologica.Model.Domicilio;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import com.example.dh.ClinicaOdontologica.Util.Util;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDaoH2 implements IDaoRepository<Paciente> {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/Integrador;INIT=RUNSCRIPT FROM 'ClinicaOdontologica/create.sql'"; //con esta instrucción cuando se conecta a la base ejecuta el script de sql que esta en el archivo create.sql
    private final static String DB_USER = "sa";
    private final static String DB_PASSWORD = "";

    private DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
    private static final Logger logger = Logger.getLogger(PacienteDaoH2.class); //Objeto logger para empleo de logs.
    @Override
    public Paciente agregar(Paciente paciente) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            //2.1 guardo el domicilio del paciente para poder usar el id en el campo domicilio_id de la tabla pacientes
            Domicilio domicilio = domicilioDaoH2.agregar(paciente.getDomicilio());//guardo el domicilio del paciente en la tabla domicilios.
            paciente.getDomicilio().setId(domicilio.getId()); // al domicilio del paciente, le seteo como id, el del domicilio guardado en el paso anterior.
            //2.2Creo una sentencia
            preparedStatement = connection.prepareStatement("INSERT INTO pacientes (nombre,apellido,dni,fecha_ingreso, domicilio_id) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, paciente.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setString(1, paciente.getNombre());
                preparedStatement.setString(2, paciente.getApellido());
                preparedStatement.setInt(3, paciente.getDni());
                //Hay que convertir el LocalDate en sql.LocalDate (son clases distintas)
                preparedStatement.setDate(4, Util.utilDateToSqlDate(paciente.getFechaIngreso()));
                preparedStatement.setLong(5,paciente.getDomicilio().getId());

                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al guardar el paciente.", e);
            throw e;
        }
        logger.info("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " ha sido guardado correctamente en la base de datos");
        return paciente;
    }

    @Override
    public Paciente buscarPorId(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Paciente paciente = null;
        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM pacientes where id = ?");

            try {
                //3. Seteo atributos y ejecuto una sentencia SQL
                preparedStatement.setLong(1, id);
                ResultSet result = preparedStatement.executeQuery();
                //4 Obtener resultados
                while (result.next()) {
                    Long idPaciente = result.getLong("id");
                    String nombre = result.getString("nombre");
                    String apellido = result.getString("apellido");
                    int dni = result.getInt("dni");
                    java.sql.Date fechaIngreso = result.getDate("fecha_ingreso");
                    Long idDomicilio = result.getLong("domicilio_id");
                    Domicilio domicilio = domicilioDaoH2.buscarPorId(idDomicilio);
                    paciente = new Paciente(idPaciente,nombre,apellido,dni,fechaIngreso,domicilio);
                }
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("No se encuentra el paciente con el id solicitado.", e);
            throw e;
        }
        logger.info("El paciente con id: " + paciente.getId() + " ha sido encontrado en la base de datos");
        return paciente;
    }

    @Override
    public List<Paciente> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Paciente> pacientes = new ArrayList<>(); //creamos listado vacio de pacientes
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT *  FROM pacientes");

            try {
                //3 Ejecutar una sentencia SQL
                ResultSet result = preparedStatement.executeQuery();

                //4 Obtener resultados
                while (result.next()) {
                    Long idPaciente = result.getLong("id");
                    String nombre = result.getString("nombre");
                    String apellido = result.getString("apellido");
                    int dni = result.getInt("dni");
                    java.sql.Date fechaIngreso = result.getDate("fecha_ingreso");
                    Long idDomicilio = result.getLong("domicilio_id");
                    Domicilio domicilio = domicilioDaoH2.buscarPorId(idDomicilio);
                    Paciente paciente = new Paciente(idPaciente,nombre,apellido,dni,fechaIngreso,domicilio);
                    pacientes.add(paciente); //agregamos el paciente al listado.
                }

            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar mostrar todos los pacientes registrados.", e);
            throw e;
        }
        logger.info("Se han encontrado pacientes en la base de datos: " + pacientes);
        return pacientes;
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            //2.1 Actualizo el domicilio
            Domicilio domicilio = domicilioDaoH2.actualizar(paciente.getDomicilio());
            //2.2Creo una sentencia
            preparedStatement = connection.prepareStatement("UPDATE pacientes SET nombre = ?, apellido = ?, dni = ?, fecha_ingreso = ?, domicilio_id = ? WHERE id = ?");
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, paciente.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setString(1, paciente.getNombre());
                preparedStatement.setString(2, paciente.getApellido());
                preparedStatement.setInt(3, paciente.getDni());
                preparedStatement.setDate(4,Util.utilDateToSqlDate(paciente.getFechaIngreso()));
                preparedStatement.setLong(5, paciente.getDomicilio().getId());
                preparedStatement.setLong(6, paciente.getId());
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar actualizar el paciente.", e);
            throw e;
        }
        logger.info("El paciente " + paciente.getNombre() + " " + paciente.getApellido() + " ha sido actualizado correctamente en la base de datos");
        return paciente;
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
            preparedStatement = connection.prepareStatement("DELETE FROM pacientes where id = ?");

            try {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar el paciente con el id solicitado.", e);
            throw e;
        }
        logger.info("El paciente con id: " + id + " ha sido eliminado correctamente.");

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
            preparedStatement = connection.prepareStatement("DELETE FROM pacientes");

            try {
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar todos los pacientes.", e);
            throw e;
        }
        logger.info("Todos los pacientes han sido eliminado correctamente.");

    }
}
