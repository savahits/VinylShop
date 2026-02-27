package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.artist.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.artist.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.domain.Artist;
import ru.shmelev.vinylshop.mappers.ArtistToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.GenreRepository;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistToDtoMapper artistToDtoMapper;
    private final GenreRepository genreRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, ArtistToDtoMapper artistToDtoMapper,
                         GenreRepository genreRepository) {
        this.artistRepository = artistRepository;
        this.artistToDtoMapper = artistToDtoMapper;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public Page<MultipleArtistsShowDTO> getArtistsByGenreId(Long genreId, Pageable pageable) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого жанра нет!");
        }

        Page<Artist> artistPage = artistRepository.findByGenresId(genreId, pageable);
        return artistPage.map(artistToDtoMapper::toMultipleShowDTO);
    }

    @Transactional(readOnly = true)
    public Page<MultipleArtistsShowDTO> getAllArtists(Pageable pageable) {
        Page<Artist> artistPage = artistRepository.findAll(pageable);
        return artistPage.map(artistToDtoMapper::toMultipleShowDTO);
    }

    @Deprecated
    public List<MultipleArtistsShowDTO> getAllArtists() {
        return artistToDtoMapper.toShowDTOList(artistRepository.findAll());
    }

    public ArtistShowDTO getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(artistToDtoMapper::toShowDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!"));
    }

    public void deleteArtistById(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!");
        }
        artistRepository.deleteById(id);
    }

}
