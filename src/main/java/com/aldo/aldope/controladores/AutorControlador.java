package com.aldo.aldope.controladores;

import com.aldo.aldope.entidades.Autor;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelMap) {
        try {
            modelMap.addAttribute("exito", nombre);
            autorServicio.crearAutor(nombre);
        } catch (MiException me) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, me);
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelMap) {

        List<Autor> autors = autorServicio.listarAutor();
        modelMap.addAttribute("autores", autors);
        return "autor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelMap) {
        modelMap.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String name, ModelMap modelMap) {
        try {
            autorServicio.modificaAutor(name, id);
            return "redirect:../lista";
        } catch (MiException me) {
            modelMap.put("error", me.getMessage());
            return "autor_modificar.html";
        }
    }

}
