package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VinylService {

    private final ProductRepository productRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public VinylService(ProductRepository productRepository, ArtistRepository artistRepository) {
        this.productRepository = productRepository;
        this.artistRepository = artistRepository;
    }

    public List<MultipleVinylShowDTO> getVinylsByGenre(Long genreId) {
        if (!productRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого жанра нет!");
        }
        List<Product> products = productRepository.findByGenreIdAndFormatWithArtist(genreId);
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MultipleVinylShowDTO toDto(Product product) {
        String artistName = product.getArtist().getNickname();
        List<String> genreNames = product.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
        return new MultipleVinylShowDTO(
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
                product.getTitle(),
                product.getArtist().getNickname(),
                genreNames,
                product.getPrice()
        );
    }
}
