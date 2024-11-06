package com.aldo.aldope.controladores;

import com.aldo.aldope.entidades.Editorial;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre) {
        try {
            editorialServicio.crearEditorial(nombre);
        } catch (MiException me) {
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, me);
            return "editorial_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelMap) {
        List<Editorial> editorials = editorialServicio.listarEditorail();
        modelMap.addAttribute("editoriales", editorials);
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelMap) {
        modelMap.put("editorial", editorialServicio.getOne(id));
        return "editorial_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelMap) {
        try {
            editorialServicio.modificarEditorial(nombre, id);
            return "redirect:../lista";
        } catch (MiException mi) {
            modelMap.put("error", mi.getMessage());
            return "editorial_modificar.html";
        }
    }
}
