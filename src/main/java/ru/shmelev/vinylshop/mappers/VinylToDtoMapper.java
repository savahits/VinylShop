package ru.shmelev.vinylshop.mappers;

import org.springframework.stereotype.Component;
import ru.shmelev.vinylshop.DTO.*;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.domain.Product.Track;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class VinylToDtoMapper {

    public VinylShowDTO toVinylShowDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new VinylShowDTO(
                product.getId(),
                product.getTitle(),
                mapArtist(product),
                product.getLabel(),
                product.getCountry(),
                product.getReleaseYear(),
                product.getTotalDuration(),
                product.getPrice(),
                product.getFormat(),
                mapTracklist(product.getTracklist()),
                mapOtherAttributes(product.getOtherAttributes())
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

    private List<TrackDTO> mapTracklist(List<Track> tracklist) {
        if (tracklist == null || tracklist.isEmpty()) {
            return Collections.emptyList();
        }

        return tracklist.stream()
                .map(this::mapTrack)
                .collect(Collectors.toList());
    }

    private TrackDTO mapTrack(Track track) {
        if (track == null) {
            return null;
        }
        return new TrackDTO(
                track.getPosition(),
                track.getTitle(),
                track.getDuration()
        );
    }

    private Map<String, Object> mapOtherAttributes(Map<String, Object> otherAttributes) {
        return otherAttributes != null ? otherAttributes : Collections.emptyMap();
    }

    public List<VinylShowDTO> toVinylShowDTOList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream()
                .map(this::toVinylShowDTO)
                .collect(Collectors.toList());
    }
}