package com.example.dh.ClinicaOdontologica.Repository.Impl;
import com.example.dh.ClinicaOdontologica.Model.Domicilio;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDaoH2 implements IDaoRepository<Domicilio> {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/Integrador;INIT=RUNSCRIPT FROM 'ClinicaOdontologica/create.sql'"; //con esta instrucción cuando se conecta a la base ejecuta el script de sql que esta en el archivo create.sql
    private final static String DB_USER = "sa";
    private final static String DB_PASSWORD = "";
    private static final Logger logger = Logger.getLogger(DomicilioDaoH2.class); //Objeto logger para empleo de logs.
    @Override
    public Domicilio agregar(Domicilio domicilio) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("INSERT INTO domicilios (calle,numero, localidad, provincia) VALUES(?,?,?,?)");
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, domicilio.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setString(1, domicilio.getCalle());
                preparedStatement.setInt(2, domicilio.getNumero());
                preparedStatement.setString(3, domicilio.getLocalidad());
                preparedStatement.setString(4,domicilio.getProvincia());
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al guardar el domicilio.", e);
            throw e;
        }
        logger.info("El domicilio: " + domicilio.getCalle() + " " + domicilio.getNumero() +", " + domicilio.getLocalidad()+", " + domicilio.getProvincia()+ " ha sido guardado correctamente en la base de datos");
        return domicilio;
    }

    @Override
    public Domicilio buscarPorId(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Domicilio domicilio = null;
        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("SELECT * FROM domicilios where id = ?");

            try {
                //3. Seteo atributos y ejecuto una sentencia SQL
                preparedStatement.setLong(1, id);
                ResultSet result = preparedStatement.executeQuery();
                //4 Obtener resultados
                while (result.next()) {
                    Long idDomicilio = result.getLong("id");
                    String calle = result.getString("calle");
                    int numero = result.getInt("numero");
                    String localidad = result.getString("localidad");
                    String provincia = result.getString("provincia");
                    domicilio = new Domicilio(idDomicilio,calle,numero,localidad,provincia);
                }
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("No se encuentra el domicilio con el id solicitado.", e);
            throw e;
        }
        logger.info("El domicilio con id: " + domicilio.getId() + " ha sido encontrado en la base de datos");
        return domicilio;
    }

    @Override
    public List<Domicilio> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Domicilio> domicilios = new ArrayList<>(); //creamos listado vacio de domicilios
        try {
            //1 Levantar el driver y Conectarnos
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2 Crear una sentencia
            preparedStatement = connection.prepareStatement("SELECT *  FROM domicilios");

            try {
                //3 Ejecutar una sentencia SQL
                ResultSet result = preparedStatement.executeQuery();

                //4 Obtener resultados
                while (result.next()) {
                    Long idDomicilio = result.getLong("id");
                    String calle = result.getString("calle");
                    int numero = result.getInt("numero");
                    String localidad = result.getString("localidad");
                    String provincia = result.getString("provincia");
                    Domicilio domicilio = new Domicilio(idDomicilio,calle,numero,localidad,provincia);

                    domicilios.add(domicilio); //agregamos domicilio a la lista
                }

            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar mostrar todos los domicilios registrados.", e);
            throw e;
        }
        logger.info("Se han encontrado domicilios en la base de datos: " + domicilios);
        return domicilios;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1.Levantar driver y conectarse.
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //2.Creo una sentencia
            preparedStatement = connection.prepareStatement("UPDATE domicilios SET calle = ?, numero = ?, localidad = ?, provincia = ? WHERE id = ?");
            //como el id está seteado como autoincremental en la DB, no se lo pasamos.
            //preparedStatement.setInt(1, domicilio.getId());

            //3. Seteo atributos y ejecuto una sentencia SQL
            try {
                preparedStatement.setString(1,domicilio.getCalle());
                preparedStatement.setInt(2,domicilio.getNumero());
                preparedStatement.setString(3,domicilio.getLocalidad());
                preparedStatement.setString(4,domicilio.getProvincia());
                preparedStatement.setLong(5,domicilio.getId());
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar actualizar el domicilio.", e);
            throw e;
        }
        logger.info("El domicilio con id:  " + domicilio.getId() + " ha sido actualizado correctamente en la base de datos");
        return domicilio;
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
            preparedStatement = connection.prepareStatement("DELETE FROM domicilios where id = ?");

            try {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar el domicilio con el id solicitado.", e);
            throw e;
        }
        logger.info("El domicilio con id: " + id + " ha sido eliminado correctamente.");

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
            preparedStatement = connection.prepareStatement("DELETE FROM domicilios");

            try {
                preparedStatement.executeUpdate();
            } finally {
                //cierra el statement incluso si ocurre algun error al setear atributos o ejecutar la sentencia sql.
                preparedStatement.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Ha ocurrido un error al intentar eliminar todos los domicilios.", e);
            throw e;
        }
        logger.info("Todos los domicilios han sido eliminado correctamente.");

    }
}
