package com.example.dh.ClinicaOdontologica.Model;

import java.sql.Date;
import java.time.LocalDate;

public class Paciente {
    //Atributos
    private Long id;
    private String nombre;
    private String apellido;
    private int Dni;
    private Date fechaIngreso;
    private Domicilio domicilio;

    //Constructor con id

    public Paciente(Long id, String nombre, String apellido, int dni, Date fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.Dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }


    //Constructor sin id

    public Paciente(String nombre, String apellido, int dni, Date fechaIngreso, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.Dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    //Constructor vacío

    public Paciente() {

    }

    //Getters y Setters (accessor methods)

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return Dni;
    }

    public void setDni(int dni) {
        Dni = dni;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
}
