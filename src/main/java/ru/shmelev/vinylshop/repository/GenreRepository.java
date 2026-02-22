package ru.shmelev.vinylshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    boolean existsByName(String name);
}
