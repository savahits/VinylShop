package ru.shmelev.vinylshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.VinylShowDTO;
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
    public VinylService(ProductRepository productRepository, GenreRepository genreRepository, ArtistRepository artistRepository, VinylToDtoMapper vinylToDtoMapper) {
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

        return products.stream().map(vinylToDtoMapper::mapToMultipleVinylShowDTO).collect(Collectors.toList());
    }


    public List<MultipleVinylShowDTO> getArtistVinyls(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!");
        }

        List<Product> vinyls = productRepository.findAllVinylsByArtistIdWithGenres(artistId);

        return vinyls.stream().map(vinylToDtoMapper::mapToMultipleVinylShowDTO).collect(Collectors.toList());
    }



    @Transactional
    public VinylShowDTO getVinylById(Long id) {
        return productRepository.findVinylById(id).map(vinylToDtoMapper::toVinylShowDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого винила нет!"));
    }

    public List<MultipleVinylShowDTO> getAllVinyls() {
        List<Product> products = productRepository.findAllVinyls();
        return products.stream().map(vinylToDtoMapper::mapToMultipleVinylShowDTO).collect(Collectors.toList());
    }

}