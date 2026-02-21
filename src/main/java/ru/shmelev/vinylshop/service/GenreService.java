package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.domain.Artist;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.mappers.ArtistToDtoMapper;
import ru.shmelev.vinylshop.mappers.GenreToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreToDtoMapper genreToDtoMapper;
    private final ArtistRepository artistRepository;
    private final ArtistToDtoMapper artistToDtoMapper;

    @Autowired
    public GenreService(GenreRepository genreRepository, GenreToDtoMapper genreToDtoMapper, ArtistRepository artistRepository
    , ArtistToDtoMapper artistToDtoMapper) {
        this.genreRepository = genreRepository;
        this.genreToDtoMapper = genreToDtoMapper;
        this.artistRepository = artistRepository;
        this.artistToDtoMapper = artistToDtoMapper;
    }

    public List<GenreResponseDTO> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genreToDtoMapper.convert(genres);
    }

    @Transactional(readOnly = true)
    public List<MultipleArtistsShowDTO> getArtistsByGenreId(Long genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Жанр не найден");
        }

        List<Artist> artists = artistRepository.findByGenresId(genreId);
        return artistToDtoMapper.toShowDTOList(artists);
    }

    public void deleteGenreById(Long genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Жанр не найден");
        }

        genreRepository.deleteById(genreId);
    }

}
