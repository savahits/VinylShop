package ru.shmelev.vinylshop.mappers;

import org.springframework.stereotype.Component;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;
import ru.shmelev.vinylshop.domain.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CDToDtoMapper{

    public MultipleCDShowDTO mapToVinylShowDTO(Product product) {
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
}
