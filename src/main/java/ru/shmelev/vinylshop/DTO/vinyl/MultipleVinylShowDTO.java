package ru.shmelev.vinylshop.DTO.vinyl;

import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record MultipleVinylShowDTO(
        Long id,
        String title,
        String artistName,
        List<GenreResponseDTO> genres,
        BigDecimal price
) {
}
