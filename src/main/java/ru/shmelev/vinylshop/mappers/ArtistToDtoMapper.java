package ru.shmelev.vinylshop.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shmelev.vinylshop.DTO.artist.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.artist.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.domain.Artist;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistToDtoMapper {

    private final GenreToDtoMapper genreToDtoMapper;

    @Autowired
    public ArtistToDtoMapper(GenreToDtoMapper genreToDtoMapper) {
        this.genreToDtoMapper = genreToDtoMapper;
    }

    public ArtistShowDTO toShowDTO(Artist artist) {
        if (artist == null) {
            return null;
        }
        return new ArtistShowDTO(
                artist.getId(),
                artist.getNickname(),
                artist.getDescription(),
                genreToDtoMapper.convert(artist.getGenres()),
                artist.getDiscography()
        );
    }

    public MultipleArtistsShowDTO toMultipleShowDTO(Artist artist) {
        if (artist == null) {
            return null;
        }
        return new MultipleArtistsShowDTO(
                artist.getId(),
                artist.getNickname()
        );
    }

    public List<MultipleArtistsShowDTO> toShowDTOList(List<Artist> artists) {
        if (artists == null) {
            return List.of();
        }
        return artists.stream()
                .map(this::toMultipleShowDTO)
                .collect(Collectors.toList());
    }

}