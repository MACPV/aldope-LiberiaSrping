package com.aldo.aldope.servicios;

import com.aldo.aldope.entidades.Usuario;
import com.aldo.aldope.enumeraciones.Rol;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(
            String nombre,
            String email,
            String password,
            String password2
    ) throws MiException {
        validar(nombre, email, password, password2);
        if (!password.equals(password2)) {
            throw new MiException("Contraseñas distintas");

        }
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    private void validar(String nombre, String email, String password, String password2) throws MiException {


        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new MiException("el email no puede ser nulo o estar vacío");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attributes.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }

        return null;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public void cambiarRol(String id){
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(id);

        if(usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();

            if (usuario.getRol().equals(Rol.USER)) {
                usuario.setRol(Rol.ADMIN);
            } else if (usuario.getRol().equals(Rol.ADMIN)) {
                usuario.setRol(Rol.USER);
            }

            usuarioRepositorio.save(usuario);
        }
    }
}
