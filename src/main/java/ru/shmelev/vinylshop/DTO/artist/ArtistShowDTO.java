package ru.shmelev.vinylshop.DTO.artist;

import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;
import ru.shmelev.vinylshop.domain.Artist;

import java.util.List;

public record ArtistShowDTO(
        Long id,
        String nickname,
        String description,
        List<GenreResponseDTO> genres,
        List<Artist.DiscographyItem> discography
) {
}
