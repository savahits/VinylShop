package ru.shmelev.vinylshop.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.genres WHERE p.artist.id = :artistId AND p.format = 'vinyl'")
    List<Product> findAllVinylsByArtistId(@Param("artistId") Long artistId);

    @Query("SELECT p FROM Product p JOIN FETCH p.genres WHERE p.artist.id = :artistId AND p.format = 'cd'")
    List<Product> findAllCDByArtistId(@Param("artistId") Long artistId);

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN p.genres g WHERE g.id = :genreId AND p.format = 'vinyl'")
    List<Product> findAllVinylsByGenreId(@Param("genreId") Long genreId);

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN p.genres g WHERE g.id = :genreId AND p.format = 'cd'")
    List<Product> findAllCDByGenreId(@Param("genreId") Long genreId);

    @Query("select p from Product p where p.format = 'vinyl'")
    List<Product> findAllVinyls();

    @Query("select p from Product p where p.format = 'cd'")
    List<Product> findAllCD();

    @Query("select p from Product p where p.format = 'vinyl' and p.id = :vinylId")
    Optional<Product> findVinylById(@Param("vinylId") Long vinylId);

    @Query("select p from Product p where p.format = 'cd' and p.id = :CDId")
    Optional<Product> findCDById(@Param("CDId") Long cdId);

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN FETCH p.genres WHERE p.format = 'vinyl' AND p.id != :excludeVinylId")
    List<Product> findOtherArtistVinyls(@Param("artistId") Long artistId, @Param("excludeVinylId") Long excludeVinylId, Pageable pageable);

}