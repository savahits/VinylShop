package ru.shmelev.vinylshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.genres WHERE p.artist.id = :artistId AND p.format = 'vinyl'")
    List<Product> findAllVinylsByArtistIdWithGenres(@Param("artistId") Long artistId);
}