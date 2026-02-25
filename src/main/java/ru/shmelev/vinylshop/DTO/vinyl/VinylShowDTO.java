package ru.shmelev.vinylshop.DTO.vinyl;

import ru.shmelev.vinylshop.DTO.TrackDTO;
import ru.shmelev.vinylshop.DTO.artist.ArtistDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record VinylShowDTO(
        Long id,
        String title,
        ArtistDTO artist,
        List<GenreResponseDTO> genres,
        String label,
        String country,
        Integer releaseYear,
        Integer totalDuration,
        BigDecimal price,
        String format,
        List<TrackDTO> tracklist,
        Map<String, Object> otherAttributes
) {}

