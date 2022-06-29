package com.example.dh.ClinicaOdontologica.Repository;
import com.example.dh.ClinicaOdontologica.Model.Odontologo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {

}
