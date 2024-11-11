package com.aldo.aldope.controladores;

import com.aldo.aldope.entidades.Usuario;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String password2,
            ModelMap modelMap
    ) {
        try {
            usuarioServicio.registrar(nombre, email, password, password2);
            modelMap.put("exito", "registro exitoso");
            return "index.html";
        } catch (MiException me) {
            modelMap.put("error", me.getMessage());

            return "registro.html";
        }


    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelMap) {
        if (error != null) {
            modelMap.put("error", "Usuario o contraseña invalidos");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "inicio.html";
    }


}
