package ru.shmelev.vinylshop.mappers;

import org.springframework.stereotype.Component;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;
import ru.shmelev.vinylshop.domain.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreToDtoMapper {

    public GenreResponseDTO convert(Genre genre) {
        if (genre == null) {
            return null;
        }
        return new GenreResponseDTO(genre.getId(), genre.getName());
    }

    public List<GenreResponseDTO> convert(List<Genre> genres) {
        if (genres == null) {
            return List.of();
        }
        return genres.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public List<GenreResponseDTO> convert(Set<Genre> genres) {
        if (genres == null) {
            return List.of();
        }
        return genres.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}