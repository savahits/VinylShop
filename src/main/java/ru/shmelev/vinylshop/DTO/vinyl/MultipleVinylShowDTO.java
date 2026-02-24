package ru.shmelev.vinylshop.DTO.vinyl;

import java.math.BigDecimal;
import java.util.List;

public record MultipleVinylShowDTO(
        Long id,
        String title,
        String artistName,
        List<String> genres,
        BigDecimal price
) {
}
