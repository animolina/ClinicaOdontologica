package com.example.dh.ClinicaOdontologica.Model;
import java.sql.Date;


public class Turno {
    //atributos
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private Date fechaYhora;

    //Constructor con id

    public Turno(Long id, Paciente paciente, Odontologo odontologo, Date fechaYhora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaYhora = fechaYhora;
    }

    //Constructor sin id

    public Turno(Paciente paciente, Odontologo odontologo, Date fechaYhora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaYhora = fechaYhora;
    }
    //Constructor vacío


    public Turno() {

    }

    //Getters y Setters

    public Long getId() {
        return id;
    }


    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Date getFechaYhora() {
        return fechaYhora;
    }

    public void setFechaYhora(Date fechaYhora) {
        this.fechaYhora = fechaYhora;
    }
}
