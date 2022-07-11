package com.example.dh.ClinicaOdontologica.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="Usuarios")
public class AppUsuario implements UserDetails {
    @Id
    @SequenceGenerator(name = "usuario_sequence",sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    private Long id;
    private String usuarioNombre;
    private String email;
    private String contrasenia;
    @Enumerated(EnumType.STRING)
    private AppUsuarioRoles usuarioRol;

    //Sobrescritura de métodos de la interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usuarioRol.name());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return usuarioNombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //Constructor vacío
    public AppUsuario() {
    }

    //Constructor sin id

    public AppUsuario(String usuarioNombre, String email, String contrasenia, AppUsuarioRoles usuarioRol) {
        this.usuarioNombre = usuarioNombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.usuarioRol = usuarioRol;
    }

    //Constructor con id

    public AppUsuario(Long id, String usuarioNombre, String email, String contrasenia, AppUsuarioRoles usuarioRol) {
        this.id = id;
        this.usuarioNombre = usuarioNombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.usuarioRol = usuarioRol;
    }


    //Getters y Setters (accessor methods)


    public Long getId() {
        return id;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public AppUsuarioRoles getUsuarioRol() {
        return usuarioRol;
    }

    public void setUsuarioRol(AppUsuarioRoles usuarioRol) {
        this.usuarioRol = usuarioRol;
    }
}
