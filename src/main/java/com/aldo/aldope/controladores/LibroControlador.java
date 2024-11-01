package com.aldo.aldope.controladores;

import com.aldo.aldope.entidades.Autor;
import com.aldo.aldope.entidades.Editorial;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.servicios.AutorServicio;
import com.aldo.aldope.servicios.EditorialServicio;
import com.aldo.aldope.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
        try {
            List<Autor> autores = autorServicio.listarAutor();
            List<Editorial> editoriales = editorialServicio.listarEditorail();

            System.out.println("Autores: " + autores); // Verificar si trae datos
            System.out.println("Editoriales: " + editoriales); // Verificar si trae datos


            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_form.html";

        } catch (Exception e) {
            e.printStackTrace(); // Imprime el stack trace completo para analizar el error

            modelo.put("error", e.getMessage());
            return "libro_form.html";
        }
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam(required = false) Long isbn,
            @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam String idAutor,
            @RequestParam String idEditorial,
            ModelMap modelo
    ) {

        try {
            libroServicio.crearLibro(titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro fue cargado correctamente!");

            return "redirect:/"; // Redirigimos al index después de un registro exitoso

        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutor();
            List<Editorial> editoriales = editorialServicio.listarEditorail();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());

            return "libro_form.html";
        }
    }
}