package ru.shmelev.vinylshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Artist;

@Repository
public interface ArtistRepository  extends JpaRepository<Artist, Long> {
}
