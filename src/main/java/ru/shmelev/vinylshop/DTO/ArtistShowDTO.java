package ru.shmelev.vinylshop.DTO;

import ru.shmelev.vinylshop.domain.Artist;

import java.util.List;

public record ArtistShowDTO(
        Long id,
        String nickname,
        String description,
        List<Artist.DiscographyItem> discography
) {
}
