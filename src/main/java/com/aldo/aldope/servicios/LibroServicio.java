package com.aldo.aldope.servicios;

import com.aldo.aldope.entidades.Autor;
import com.aldo.aldope.entidades.Editorial;
import com.aldo.aldope.entidades.Libro;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.repositorios.AutorRepositorio;
import com.aldo.aldope.repositorios.EditorialRepositorio;
import com.aldo.aldope.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(
            String titulo,
            Integer ejemplares,
            String idAutor,
            String idEditorial
    ) throws MiException {
        validar(titulo, ejemplares, idAutor, idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();

        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList<>();

        libros = libroRepositorio.findAll();

        return libros;
    }

    public void modificarLibro(
            Long isbn,
            String titulo,
            Integer ejemplares,
            String idAutor,
            String idEditorial
    ) throws MiException {

        validar(titulo, ejemplares, idAutor, idEditorial);
        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(idEditorial);
        Optional<Autor> autorOptional = autorRepositorio.findById(idAutor);
        Optional<Libro> libroOptional = libroRepositorio.findById(isbn);

        Editorial editorial = new Editorial();
        Autor autor = new Autor();

        if (libroOptional.isPresent()) {

            Libro libro = libroOptional.get();
            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setEjemplares(ejemplares);

            if (optionalEditorial.isPresent()) {
                editorial = optionalEditorial.get();
                libro.setEditorial(editorial);
            }
            if (autorOptional.isPresent()) {
                autor = autorOptional.get();
                libro.setAutor(autor);
            }

            libroRepositorio.save(libro);
        }
    }

    public void validar(
            String titulo,
            Integer ejemplares,
            String idAutor,
            String idEditorial
    ) throws MiException {

        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o vacío");
        }
        if (ejemplares == null) {
            throw new MiException("Los ejemplares no pueden ser nulos");
        }
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiException("El nombre de autor no puede ser nulo o vacío");
        }
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiException("El nombre de editorial no puede ser nulo o vacío");
        }

    }

    @Transactional(readOnly = true)
    public Libro getOne(Long isbn) {
        return libroRepositorio.getReferenceById(isbn);
    }
}
