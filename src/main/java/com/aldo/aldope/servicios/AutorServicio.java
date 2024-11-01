package com.aldo.aldope.servicios;

import com.aldo.aldope.entidades.Autor;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(
            String name
    ) throws MiException {
        validar(name);
        Autor autor = new Autor();
        autor.setName(name);

        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutor() {

        return autorRepositorio.findAll();

    }

    @Transactional
    public void modificaAutor(String name, String id) throws MiException {
        validar(name);
        Optional<Autor> autorOptional = autorRepositorio.findById(id);

        if (autorOptional.isPresent()) {

            Autor autor = autorOptional.get();
            autor.setName(name);
            autorRepositorio.save(autor);
        }
    }


    public void validar(String name) throws MiException {
        if (name == null || name.isEmpty()) {
            throw new MiException("El nomnbre no puede ser nulo o vacío");
        }

    }
}
