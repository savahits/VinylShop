package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shmelev.vinylshop.DTO.GenreResponseDTO;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.mappers.GenreToDtoMapper;
import ru.shmelev.vinylshop.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreToDtoMapper genreToDtoMapper;

    @Autowired
    public GenreService(GenreRepository genreRepository, GenreToDtoMapper genreToDtoMapper) {
        this.genreRepository = genreRepository;
        this.genreToDtoMapper = genreToDtoMapper;
    }

    public List<GenreResponseDTO> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genreToDtoMapper.convert(genres);
    }

}
