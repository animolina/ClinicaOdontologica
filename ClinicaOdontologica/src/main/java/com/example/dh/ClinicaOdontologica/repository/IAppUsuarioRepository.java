package com.example.dh.ClinicaOdontologica.repository;
import com.example.dh.ClinicaOdontologica.model.AppUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface IAppUsuarioRepository extends JpaRepository<AppUsuario, Long>{
    //método para encontrar usuarios por email.
    Optional<AppUsuario> findByEmail(String email);
}
