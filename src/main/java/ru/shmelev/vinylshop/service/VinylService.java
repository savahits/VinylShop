package ru.shmelev.vinylshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.VinylDetailResponseDTO;
import ru.shmelev.vinylshop.DTO.vinyl.VinylShowDTO;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.mappers.VinylToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.GenreRepository;
import ru.shmelev.vinylshop.repository.ProductRepository;

import org.springframework.data.domain.PageRequest;

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

        List<Product> products = productRepository.findAllVinylsByGenreId(genreId);

        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream().map(vinylToDtoMapper::mapToMultipleVinylShowDTO).collect(Collectors.toList());
    }


    public List<MultipleVinylShowDTO> getArtistVinyls(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!");
        }

        List<Product> vinyls = productRepository.findAllVinylsByArtistId(artistId);

        return vinyls.stream().map(vinylToDtoMapper::mapToMultipleVinylShowDTO).collect(Collectors.toList());
    }

    @Transactional
    public VinylDetailResponseDTO getVinylById(Long id, boolean includeOtherArtistVinyls) {
        Product product = productRepository.findVinylById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого винила нет!"));
        VinylShowDTO vinyl = vinylToDtoMapper.toVinylShowDTO(product);

        List<MultipleVinylShowDTO> otherArtistVinyls = Collections.emptyList();
        if (includeOtherArtistVinyls) {
            List<Product> otherVinyls = productRepository.findOtherArtistVinyls(
                    product.getArtist().getId(),
                    id,
                    PageRequest.of(0, 10)
            );
            otherArtistVinyls = otherVinyls.stream()
                    .map(vinylToDtoMapper::mapToMultipleVinylShowDTO)
                    .collect(Collectors.toList());
        }

        return new VinylDetailResponseDTO(vinyl, otherArtistVinyls);
    }

    public List<MultipleVinylShowDTO> getAllVinyls() {
        List<Product> products = productRepository.findAllVinyls();
        return products.stream().map(vinylToDtoMapper::mapToMultipleVinylShowDTO).collect(Collectors.toList());
    }

    public void deleteVinylById(long vinylId){
        if (!productRepository.existsById(vinylId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого винила нет!");
        }

        productRepository.deleteById(vinylId);
    }

}