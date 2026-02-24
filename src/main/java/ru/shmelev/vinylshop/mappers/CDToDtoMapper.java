package ru.shmelev.vinylshop.mappers;

import org.springframework.stereotype.Component;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CDToDtoMapper{

    public MultipleCDShowDTO mapToCDShowDTO(Product product) {
        List<String> genreNames = product.getGenres().stream().map(Genre::getName).collect(Collectors.toList());

        return new MultipleCDShowDTO(product.getId(), product.getTitle(), product.getArtist().getNickname(), genreNames, product.getPrice());
    }
}
