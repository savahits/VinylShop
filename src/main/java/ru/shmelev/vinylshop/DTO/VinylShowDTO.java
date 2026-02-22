package ru.shmelev.vinylshop.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record VinylShowDTO(
        Long id,
        String title,
        ArtistDTO artist,
        String label,
        String country,
        Integer releaseYear,
        Integer totalDuration,
        BigDecimal price,
        String format,
        List<TrackDTO> tracklist,
        Map<String, Object> otherAttributes
) {}

