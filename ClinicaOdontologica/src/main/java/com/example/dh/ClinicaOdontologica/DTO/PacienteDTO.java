package com.example.dh.ClinicaOdontologica.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private DomicilioDTO domicilioDTO;

    //Constructor vacío
    public PacienteDTO() {
    }
    //Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DomicilioDTO getDomicilioDTO() {
        return domicilioDTO;
    }

    public void setDomicilioDTO(DomicilioDTO domicilioDTO) {
        this.domicilioDTO = domicilioDTO;
    }
}
