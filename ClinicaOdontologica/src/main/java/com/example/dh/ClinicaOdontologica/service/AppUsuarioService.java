package com.example.dh.ClinicaOdontologica.service;

import com.example.dh.ClinicaOdontologica.repository.IAppUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUsuarioService implements UserDetailsService {
    private final IAppUsuarioRepository usuarioRepository;

    @Autowired //la inyección de dependencias se hace por constructor porque es una mejor práctica que solo usar @Autowired.
    public AppUsuarioService(IAppUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow((()-> new UsernameNotFoundException("El email del usuario no ha sido encontrado")));
    }
}
