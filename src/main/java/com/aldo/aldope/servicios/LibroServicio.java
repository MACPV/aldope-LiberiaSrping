package com.aldo.aldope.servicios;

import com.aldo.aldope.entidades.Autor;
import com.aldo.aldope.entidades.Editorial;
import com.aldo.aldope.entidades.Libro;
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
    private AutorRepositorio autorRepositorio;
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(
            String titulo,
            Integer ejemplares,
            String idAutor,
            String idEditorial
    ) {
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
    ) {

        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(idEditorial);
        Optional<Autor> autorOptional = autorRepositorio.findById(idAutor);
        Optional<Libro> libroOptional = libroRepositorio.findById(isbn);

        Editorial editorial = new Editorial();
        Autor autor = new Autor();
        Libro libro = new Libro();

        if (libroOptional.isPresent()) {

            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setEjemplares(ejemplares);

            if (optionalEditorial.isPresent()) {
                editorial = optionalEditorial.get();
                libro.setEditorial(editorial);
            }
            if (autorOptional.isPresent()){
                autor = autorOptional.get();
                libro.setAutor(autor);
            }

            libroRepositorio.save(libro);
        }
    }
}
