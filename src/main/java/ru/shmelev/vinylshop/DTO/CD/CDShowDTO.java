package ru.shmelev.vinylshop.DTO.CD;

import ru.shmelev.vinylshop.DTO.TrackDTO;
import ru.shmelev.vinylshop.DTO.artist.ArtistDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record CDShowDTO(
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
) {
}