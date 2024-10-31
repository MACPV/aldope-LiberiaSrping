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
    ) {
        Autor autor = new Autor();
        autor.setName(name);

        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutor(){
        List<Autor> autors = new ArrayList<>();

        autors = autorRepositorio.findAll();

        return autors;

    }

    @Transactional
    public void modificaAutor(String name, String id){

        Optional<Autor> autorOptional = autorRepositorio.findById(id);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            autor.setName(name);
            autorRepositorio.save(autor);
        }
    }
}