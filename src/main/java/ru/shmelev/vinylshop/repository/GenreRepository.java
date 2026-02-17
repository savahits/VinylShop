package ru.shmelev.vinylshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Genre;

import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {


    @Query(value = "SELECT DISTINCT g.* FROM genres g " +
            "JOIN product_genres pg ON g.id = pg.genre_id " +
            "JOIN products p ON pg.product_id = p.id " +
            "WHERE p.artist_id = :artistId", nativeQuery = true)
    Set<Genre> findGenresByArtistId(@Param("artistId") Long artistId);
}
