package com.example.dh.ClinicaOdontologica.Repository.Impl;

import com.example.dh.ClinicaOdontologica.Model.Paciente;
import com.example.dh.ClinicaOdontologica.Repository.IDaoRepository;
import org.apache.log4j.Logger;

import java.util.List;

public class PacienteDaoH2 implements IDaoRepository<Paciente> {
    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:~/Integrador;INIT=RUNSCRIPT FROM 'ClinicaOdontologica/create.sql'"; //con esta instrucción cuando se conecta a la base ejecuta el script de sql que esta en el archivo create.sql
    private final static String DB_USER = "sa";
    private final static String DB_PASSWORD = "";
    private static final Logger logger = Logger.getLogger(PacienteDaoH2.class); //Objeto logger para empleo de logs.
    @Override
    public Paciente agregar(Paciente paciente) throws Exception {
        return null;
    }

    @Override
    public Paciente buscarPorId(Long id) throws Exception {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() throws Exception {
        return null;
    }

    @Override
    public Paciente actualizar(Paciente paciente) throws Exception {
        return null;
    }

    @Override
    public void eliminarPorId(Long id) throws Exception {

    }

    @Override
    public void eliminarTodos() throws Exception {

    }
}
