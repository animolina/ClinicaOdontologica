package com.example.dh.ClinicaOdontologica.Model;

public class Odontologo {
    //atributos
    private Long id;
    private String nombre;
    private String apellido;
    private Long matricula;

    //Constructor con id

    public Odontologo(Long id, String nombre, String apellido, Long matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    //Constructor sin id
    public Odontologo(String nombre, String apellido, Long matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }
    //Constructor vacío


    public Odontologo() {

    }
    //Getters y Setters (accessor methods)

    public Long getId() { //el id es generado en la DB, no lo debemos setear manualmente.
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

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }
}
