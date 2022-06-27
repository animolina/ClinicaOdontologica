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

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    //Sobrescritura metodo toString

    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", matricula=" + matricula +
                '}';
    }
}
