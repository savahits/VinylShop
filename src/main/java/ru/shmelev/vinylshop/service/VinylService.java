package ru.shmelev.vinylshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.DTO.VinylShowDTO;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.mappers.VinylToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.GenreRepository;
import ru.shmelev.vinylshop.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VinylService {

    private final ProductRepository productRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;
    private final VinylToDtoMapper vinylToDtoMapper;

    @Autowired
    public VinylService(ProductRepository productRepository, GenreRepository genreRepository, ArtistRepository artistRepository,
                        VinylToDtoMapper vinylToDtoMapper) {
        this.productRepository = productRepository;
        this.genreRepository = genreRepository;
        this.artistRepository = artistRepository;
        this.vinylToDtoMapper = vinylToDtoMapper;
    }

    public List<MultipleVinylShowDTO> getVinylsByGenre(Long genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого жанра нет!");
        }

        List<Product> products = productRepository.findByGenreIdAndFormatWithArtist(genreId);

        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MultipleVinylShowDTO toDto(Product product) {
        String artistName = product.getArtist() != null
                ? product.getArtist().getNickname()
                : "Unknown Artist";

        List<String> genreNames = product.getGenres() != null
                ? product.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new MultipleVinylShowDTO(
                product.getId(),
                product.getTitle(),
                artistName,
                genreNames,
                product.getPrice()
        );
    }

    public List<MultipleVinylShowDTO> getArtistVinyls(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!");
        }

        List<Product> vinyls = productRepository.findAllVinylsByArtistIdWithGenres(artistId);

        return vinyls.stream()
                .map(this::mapToVinylShowDTO)
                .collect(Collectors.toList());
    }
    private MultipleVinylShowDTO mapToVinylShowDTO(Product product) {
        List<String> genreNames = product.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toList());

        return new MultipleVinylShowDTO(
                product.getId(),
                product.getTitle(),
                product.getArtist().getNickname(),
                genreNames,
                product.getPrice()
        );
    }

    @Transactional
    public VinylShowDTO getVinylById(Long id) {
        return productRepository.findById(id)
                .map(vinylToDtoMapper::toVinylShowDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого винила нет!"));
    }

}



