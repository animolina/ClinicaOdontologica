package com.example.dh.ClinicaOdontologica.repository;
import com.example.dh.ClinicaOdontologica.model.Odontologo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {

}
