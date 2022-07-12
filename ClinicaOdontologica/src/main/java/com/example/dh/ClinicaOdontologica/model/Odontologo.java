package com.example.dh.ClinicaOdontologica.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Odontologos")
public class Odontologo {
    //atributos
    @Id
    @SequenceGenerator(name = "odontologo_sequence",sequenceName = "odontologo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odontologo_sequence")
    private Long id;
    private String nombre;
    private String apellido;
    private Long matricula;

    @OneToMany(mappedBy = "odontologo",cascade = CascadeType.ALL, fetch = FetchType.LAZY) //@OneToMany es Lazy por defecto
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

    //Constructor vacío
    public Odontologo() {
    }
    //Constructor con id
    public Odontologo(Long id, String nombre, String apellido, Long matricula, Set<Turno> turnos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
        this.turnos = turnos;
    }
    //Constructor sin id y sin turnos
    public Odontologo(String nombre, String apellido, Long matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }
    //Constructor con id, nombre, apellido y matricula
    public Odontologo(Long id, String nombre, String apellido, Long matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
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
    public Long getMatricula() {
        return matricula;
    }
    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }
    public Set<Turno> getTurnos() {
        return turnos;
    }
    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
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
