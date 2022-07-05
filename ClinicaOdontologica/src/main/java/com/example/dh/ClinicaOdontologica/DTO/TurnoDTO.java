package com.example.dh.ClinicaOdontologica.DTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDTO {
    private Long id;
    private PacienteDTO pacienteDTO;
    private OdontologoDTO odontologoDTO;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaYhora;

    //Constructor vacío
    public TurnoDTO() {
    }

    //Getters y Setters

    public Long getId() {
        return id;
    }

    public PacienteDTO getPacienteDTO() {
        return pacienteDTO;
    }

    public void setPacienteDTO(PacienteDTO pacienteDTO) {
        this.pacienteDTO = pacienteDTO;
    }

    public OdontologoDTO getOdontologoDTO() {
        return odontologoDTO;
    }

    public void setOdontologoDTO(OdontologoDTO odontologoDTO) {
        this.odontologoDTO = odontologoDTO;
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
                ", paciente=" + pacienteDTO +
                ", odontologo=" + odontologoDTO +
                ", fecha y hora=" + fechaYhora +
                '}';
    }
}
