package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.GenreResponseDTO;
import ru.shmelev.vinylshop.domain.Artist;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.mappers.ArtistToDtoMapper;
import ru.shmelev.vinylshop.mappers.GenreToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.GenreRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreToDtoMapper genreToDtoMapper;
    private final ArtistRepository artistRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, GenreToDtoMapper genreToDtoMapper, ArtistRepository artistRepository) {
        this.genreRepository = genreRepository;
        this.genreToDtoMapper = genreToDtoMapper;
        this.artistRepository = artistRepository;
    }

    public List<GenreResponseDTO> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genreToDtoMapper.convert(genres);
    }

    public void deleteGenreById(Long genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Жанр не найден");
        }

        genreRepository.deleteById(genreId);
    }

    public Set<GenreResponseDTO> getGenresByArtistId(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!"));

        return artist.getGenres().stream()
                .map(genre -> new GenreResponseDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toSet());
    }

}
