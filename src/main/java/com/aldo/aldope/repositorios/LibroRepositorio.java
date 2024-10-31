package com.aldo.aldope.repositorios;

import com.aldo.aldope.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor = :autor")
    public List<Libro> buscarPorAutor(@Param("autor") String autor);

    @Query("SELECT l FROM Libro l WHERE l.editorial = :editorial")
    public List<Libro> buscaPorEditorial(@Param("editorial") String editorial);
}
