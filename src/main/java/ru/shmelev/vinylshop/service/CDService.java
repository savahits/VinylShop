package ru.shmelev.vinylshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.CD.CDDetailResponseDTO;
import ru.shmelev.vinylshop.DTO.CD.CDShowDTO;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.VinylDetailResponseDTO;
import ru.shmelev.vinylshop.DTO.vinyl.VinylShowDTO;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.mappers.CDToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.GenreRepository;
import ru.shmelev.vinylshop.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CDService {

    private final ProductRepository productRepository;
    private final CDToDtoMapper cdToDtoMapper;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public CDService(ProductRepository productRepository, CDToDtoMapper cdToDtoMapper, GenreRepository genreRepository,
                     ArtistRepository artistRepository) {
        this.productRepository = productRepository;
        this.cdToDtoMapper = cdToDtoMapper;
        this.genreRepository = genreRepository;
        this.artistRepository = artistRepository;
    }

    public List<MultipleCDShowDTO> getAllCD() {
        List<Product> products = productRepository.findAllCD();
        return products.stream().map(cdToDtoMapper::mapToMultipleCdShowDTO).collect(Collectors.toList());
    }

    @Transactional
    public CDDetailResponseDTO getCDById(Long id, boolean includeOtherCDs) {
        Product product = productRepository.findCDById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого cd нет!"));
        CDShowDTO cd = cdToDtoMapper.toCdShowDTO(product);

        List<MultipleCDShowDTO> otherArtistCD = Collections.emptyList();
        if (includeOtherCDs) {
            List<Product> otherCDs = productRepository.findOtherArtistCDs(
                    product.getArtist().getId(),
                    id,
                    PageRequest.of(0, 10)
            );
            otherArtistCD = otherCDs.stream()
                    .map(cdToDtoMapper::mapToMultipleCdShowDTO)
                    .toList();
        }

        return new CDDetailResponseDTO(cd, otherArtistCD);
    }

    public List<MultipleCDShowDTO> getCDByGenre(Long genreId) {
        if (!genreRepository.existsById(genreId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого жанра нет!");
        }

        List<Product> products = productRepository.findAllCDByGenreId(genreId);

        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream().map(cdToDtoMapper::mapToMultipleCdShowDTO).collect(Collectors.toList());
    }

    public List<MultipleCDShowDTO> getArtistCD(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!");
        }

        List<Product> vinyls = productRepository.findAllCDByArtistId(artistId);

        return vinyls.stream().map(cdToDtoMapper::mapToMultipleCdShowDTO).collect(Collectors.toList());
    }

}
