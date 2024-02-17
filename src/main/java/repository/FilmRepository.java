package repository;

import java.util.*;
import model.FilmEntity;
import model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    List<FilmEntity> findByName(String name);
    List<FilmEntity> findFilmByGenre(GenreEntity genre);

    List<FilmEntity> findFilmByYear(String year);
}
