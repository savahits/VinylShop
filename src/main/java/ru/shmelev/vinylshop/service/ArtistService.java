package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.mappers.ArtistToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.GenreRepository;

import java.util.List;
import java.util.Set;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistToDtoMapper artistToDtoMapper;
    private final GenreRepository genreRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, ArtistToDtoMapper artistToDtoMapper, GenreRepository genreRepository) {
        this.artistRepository = artistRepository;
        this.artistToDtoMapper = artistToDtoMapper;
        this.genreRepository = genreRepository;
    }

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

    public Set<Genre> getGenresByArtistId(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!");
        }
        return genreRepository.findGenresByArtistId(artistId);
    }


}
