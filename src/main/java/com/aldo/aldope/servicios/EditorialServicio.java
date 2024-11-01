package com.aldo.aldope.servicios;

import com.aldo.aldope.entidades.Editorial;
import com.aldo.aldope.excepciones.MiException;
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
    ) throws MiException {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditorail() {

        return editorialRepositorio.findAll();

    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws MiException {
        validar(nombre);
        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(id);

        if (optionalEditorial.isPresent()) {
            Editorial editorial = optionalEditorial.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }
    }

    public void validar(String name) throws MiException {
        if (name == null || name.isEmpty()) {
            throw new MiException("El nomnbre no puede ser nulo o vacío");
        }

    }
}
