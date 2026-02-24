package ru.shmelev.vinylshop.mappers;

import org.springframework.stereotype.Component;
import ru.shmelev.vinylshop.DTO.CD.CDShowDTO;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.DTO.TrackDTO;
import ru.shmelev.vinylshop.DTO.artist.ArtistDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;
import ru.shmelev.vinylshop.domain.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CDToDtoMapper{

    public CDShowDTO toCdShowDTO(Product product) {
        if (product == null) {
            return null;
        }

        List<TrackDTO> trackDTOs = product.getTracklist().stream()
                .map(this::mapToTrackDTO)
                .collect(Collectors.toList());

        return new CDShowDTO(
                product.getId(),
                product.getTitle(),
                mapArtist(product),
                product.getLabel(),
                product.getCountry(),
                product.getReleaseYear(),
                product.getTotalDuration(),
                product.getPrice(),
                product.getFormat(),
                trackDTOs,
                product.getOtherAttributes()
        );
    }

    public MultipleCDShowDTO mapToCdShowDTO(Product product) {
        if (product == null) {
            return null;
        }

        List<GenreResponseDTO> genreDTOs = product.getGenres() != null
                ? product.getGenres().stream()
                .map(genre -> new GenreResponseDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toList())
                : Collections.emptyList();

        String artistName = product.getArtist() != null
                ? product.getArtist().getNickname()
                : "Unknown Artist";

        return new MultipleCDShowDTO(
                product.getId(),
                product.getTitle(),
                artistName,
                genreDTOs,
                product.getPrice()
        );
    }

    private TrackDTO mapToTrackDTO(Product.Track track) {
        if (track == null) {
            return null;
        }
        return new TrackDTO(
                track.getPosition(),
                track.getTitle(),
                track.getDuration()
        );
    }

    private ArtistDTO mapArtist(Product product) {
        if (product.getArtist() == null) {
            return null;
        }
        return new ArtistDTO(
                product.getArtist().getId(),
                product.getArtist().getNickname()
        );
    }
}
