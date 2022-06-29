package com.example.dh.ClinicaOdontologica.Repository;
import com.example.dh.ClinicaOdontologica.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
}
