package com.example.dh.ClinicaOdontologica.login;
import com.example.dh.ClinicaOdontologica.model.AppUsuario;
import com.example.dh.ClinicaOdontologica.model.AppUsuarioRoles;
import com.example.dh.ClinicaOdontologica.repository.IAppUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private IAppUsuarioRepository usuarioRepository;

    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public DataLoader(IAppUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Encriptar contraseñas
        BCryptPasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
        String contrasenia = passwordEncoder1.encode("usuario");
        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String contrasenia2 = passwordEncoder2.encode("administrador");
        //Crear dos instancias de usuario, User y Admin.
       AppUsuario  AppUsuario1 = new AppUsuario("usuario","usuario@gmail.com", contrasenia, AppUsuarioRoles.USER);
       AppUsuario  AppUsuario2 = new AppUsuario("administrador","administrador@gmail.com", contrasenia2, AppUsuarioRoles.ADMIN);

       //Guardo las instancias de usuario en la BD.
        if(usuarioRepository.findByEmail(AppUsuario1.getEmail()).isEmpty()) {
            usuarioRepository.save(AppUsuario1);

        }
        if(usuarioRepository.findByEmail(AppUsuario2.getEmail()).isEmpty()){
            usuarioRepository.save(AppUsuario2);
        }

    }
}
