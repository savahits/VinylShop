package ru.shmelev.vinylshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Genre;

import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {


}
