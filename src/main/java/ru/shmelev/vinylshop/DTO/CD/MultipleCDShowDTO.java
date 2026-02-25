package ru.shmelev.vinylshop.DTO.CD;

import ru.shmelev.vinylshop.DTO.artist.ArtistDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record MultipleCDShowDTO (
        Long id,
        String title,
        ArtistDTO artist,
        List<GenreResponseDTO> genres,
        BigDecimal price
){
}