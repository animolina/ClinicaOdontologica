package com.example.dh.ClinicaOdontologica.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;;
import java.time.LocalDateTime;

@Entity
@Table(name = "turnos")
public class Turno {
    //atributos
    @Id
    @SequenceGenerator(name = "turno_sequence", sequenceName = "turno_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turno_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Odontologo odontologo;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaYhora;

    //Constructor vacío
    public Turno() {
    }
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

    //Sobrescritura método toString
    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", odontologo=" + odontologo +
                ", fecha y hora=" + fechaYhora +
                '}';
    }

}
