package com.aldo.aldope.servicios;

import com.aldo.aldope.entidades.Editorial;
import com.aldo.aldope.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(
            String nombre
    ) {
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditorail() {
        List<Editorial> editorials = new ArrayList<>();

        editorials = editorialRepositorio.findAll();

        return editorials;

    }

    @Transactional
    public void modificarEditorial(String nombre, String id) {
        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(id);

        if (optionalEditorial.isPresent()) {
            Editorial editorial = optionalEditorial.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }
    }
}
