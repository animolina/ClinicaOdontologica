package com.example.dh.ClinicaOdontologica.Repository;
import com.example.dh.ClinicaOdontologica.Model.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio,Long> {
}
