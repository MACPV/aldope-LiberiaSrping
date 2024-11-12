package com.aldo.aldope.servicios;

import com.aldo.aldope.entidades.Imagen;
import com.aldo.aldope.excepciones.MiException;
import com.aldo.aldope.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardarImagen(MultipartFile file) throws MiException {

        if (file != null) {
            try {
                Imagen imagen = new Imagen();

                imagen.setMime(file.getContentType());
                imagen.setNombre(file.getName());
                imagen.setContenido(file.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return null;

    }

    @Transactional
    public Imagen actualizarImagen(String id, MultipartFile file) throws MiException {
        if (file != null) {
            try {
                Imagen imagen = new Imagen();

                if (id != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(file.getContentType());
                imagen.setNombre(file.getName());
                imagen.setContenido(file.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());

            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Imagen> listarTodos() {
        return imagenRepositorio.findAll();
    }


}

