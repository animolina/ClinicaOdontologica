package com.example.dh.ClinicaOdontologica.DTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDTO {
    private Long id;
    private PacienteDTO pacienteDTO;
    private OdontologoDTO odontologoDTO;
    private Date fechaYhora;

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

    public Date getFechaYhora() {
        return fechaYhora;
    }

    public void setFechaYhora(Date fechaYhora) {
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
