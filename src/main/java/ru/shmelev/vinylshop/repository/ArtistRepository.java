package ru.shmelev.vinylshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shmelev.vinylshop.domain.Artist;

public interface ArtistRepository  extends JpaRepository<Long, Artist> {
}
