package ru.shmelev.vinylshop.mappers;

import org.springframework.stereotype.Component;
import ru.shmelev.vinylshop.DTO.ArtistsShowDTO;
import ru.shmelev.vinylshop.domain.Artist;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistToDtoMapper {

    public ArtistsShowDTO toShowDTO(Artist artist) {
        if (artist == null) {
            return null;
        }
        return new ArtistsShowDTO(
                artist.getId(),
                artist.getNickname()
        );
    }

    public List<ArtistsShowDTO> toShowDTOList(List<Artist> artists) {
        if (artists == null) {
            return List.of();
        }
        return artists.stream()
                .map(this::toShowDTO)
                .collect(Collectors.toList());
    }

}