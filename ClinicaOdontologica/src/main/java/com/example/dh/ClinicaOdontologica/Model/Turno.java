package com.example.dh.ClinicaOdontologica.Model;

import java.time.LocalDateTime;

public class Turno {
    //atributos
    private Long id;
    private Paciente paciente;
    private Odontologo odontologo;
    private LocalDateTime fechaYhora;

    //Constructor con id

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaYhora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaYhora = fechaYhora;
    }

    //Constructor sin id

    public Turno(Paciente paciente, Odontologo odontologo, LocalDateTime fechaYhora) {
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

    public LocalDateTime getFechaYhora() {
        return fechaYhora;
    }

    public void setFechaYhora(LocalDateTime fechaYhora) {
        this.fechaYhora = fechaYhora;
    }
}
