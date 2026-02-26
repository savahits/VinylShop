package ru.shmelev.vinylshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Artist;

import java.util.List;

@Repository
public interface ArtistRepository  extends JpaRepository<Artist, Long> {

    @Deprecated
    List<Artist> findByGenresId(Long genreId);

    @Query(value = "SELECT a FROM Artist a JOIN a.genres g WHERE g.id = :genreId",
            countQuery = "SELECT COUNT(a) FROM Artist a JOIN a.genres g WHERE g.id = :genreId")
    Page<Artist> findByGenresId(@Param("genreId") Long genreId, Pageable pageable);
}
