package com.aldo.aldope.repositorios;

import com.aldo.aldope.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepositorio extends JpaRepository<Imagen, String > {
}
