package com.example.dh.ClinicaOdontologica.Repository;
import com.example.dh.ClinicaOdontologica.Model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {
}
