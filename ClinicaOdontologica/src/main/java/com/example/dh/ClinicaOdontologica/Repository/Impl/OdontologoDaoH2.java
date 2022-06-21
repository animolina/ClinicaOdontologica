package com.example.dh.ClinicaOdontologica.Repository.Impl;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDaoRepository<Odontologo> {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/Integrador;INIT=RUNSCRIPT FROM 'ClinicaOdontologica/create.sql'"; //con esta instrucción cuando se conecta a la base ejecuta el script de sql que esta en el archivo create.sql
    private final static String DB_USER = "sa";
    private final static String DB_PASSWORD = "";
    private static final Logger logger = Logger.getLogger(OdontologoDaoH2.class); //Objeto logger para empleo de logs.

    //constructor vacío
    public OdontologoDaoH2() {

    }

    //Sobrescritura de los métodos de la interfaz IDaoRepository.
    @Override
    public Odontologo agregar(Odontologo odontologo) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("INSERT INTO odontologos (nombre,apellido,matricula) VALUES(?,?,?)");
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, odontologo.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setString(1, odontologo.getNombre());
                preparedStatement.setString(2, odontologo.getApellido());
                preparedStatement.setLong(3, odontologo.getMatricula());
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al guardar el usuario.", e);
            throw e;
        }
        logger.info("El odontologo " + odontologo.getNombre() + " " + odontologo.getApellido() + " ha sido guardado correctamente en la base de datos");
        return odontologo;
    }

    @Override
    public Odontologo buscarPorId(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Odontologo odontologo = null;
        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM odontologos where id = ?");

            try {
                //3. Seteo atributos y ejecuto una sentencia SQL
                preparedStatement.setLong(1, id);
                ResultSet result = preparedStatement.executeQuery();
                //4 Obtener resultados
                while (result.next()) {
                    Long idOdontologo = result.getLong("id");
                    String nombre = result.getString("nombre");
                    String apellido = result.getString("apellido");
                    Long matricula = result.getLong("matricula");
                    odontologo = new Odontologo(idOdontologo, nombre, apellido, matricula);
                }
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("No se encuentra el usuario con el id solicitado.", e);
            throw e;
        }
        logger.info("El odontologo con id: " + odontologo.getId() + " ha sido encontrado en la base de datos");
        return odontologo;
    }

    @Override
    public List buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Odontologo> odontologos = new ArrayList<>(); //creamos listado vacio de odontologos
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT *  FROM odontologos");

            try {
                //3 Ejecutar una sentencia SQL
                ResultSet result = preparedStatement.executeQuery();

                //4 Obtener resultados
                while (result.next()) {
                    Long idOdontologo = result.getLong("id");
                    String nombre = result.getString("nombre");
                    String apellido = result.getString("apellido");
                    Long matricula = result.getLong("matricula");
                    Odontologo odontologo = new Odontologo(idOdontologo, nombre, apellido, matricula);

                    odontologos.add(odontologo); //agregamos odontologo a la lista
                }

            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar mostrar todos los odontologos registrados.", e);
            throw e;
        }
        logger.info("Se han encontrado odontologos en la base de datos: " + odontologos);
        return odontologos;
    }


    @Override
    public Odontologo actualizar(Odontologo odontologo) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("UPDATE odontologos SET nombre = ?, apellido = ?, matricula = ? WHERE id = ?");
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, odontologo.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setString(1, odontologo.getNombre());
                preparedStatement.setString(2, odontologo.getApellido());
                preparedStatement.setLong(3, odontologo.getMatricula());
                preparedStatement.setLong(4,odontologo.getId());
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar actualizar el usuario.", e);
            throw e;
        }
        logger.info("El odontologo " + odontologo.getNombre() + " " + odontologo.getApellido() + " ha sido actualizado correctamente en la base de datos");
        return odontologo;
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
            preparedStatement = connection.prepareStatement("DELETE FROM odontologos where id = ?");

            try {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar el usuario con el id solicitado.", e);
            throw e;
        }
        logger.info("El odontologo con id: " + id + " ha sido eliminado correctamente.");

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
            preparedStatement = connection.prepareStatement("DELETE FROM odontologos");

            try {
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar todos los odontólogos.", e);
            throw e;
        }
        logger.info("Todos los odontólogos han sido eliminado correctamente.");
    }

}
