package ru.shmelev.vinylshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.CD.CDShowDTO;
import ru.shmelev.vinylshop.DTO.CD.MultipleCDShowDTO;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.mappers.CDToDtoMapper;
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

    @Autowired
    public CDService(ProductRepository productRepository, CDToDtoMapper cdToDtoMapper, GenreRepository genreRepository) {
        this.productRepository = productRepository;
        this.cdToDtoMapper = cdToDtoMapper;
        this.genreRepository = genreRepository;
    }

    public List<MultipleCDShowDTO> getAllCD() {
        List<Product> products = productRepository.findAllCD();
        return products.stream().map(cdToDtoMapper::mapToMultipleCdShowDTO).collect(Collectors.toList());
    }

    @Transactional
    public CDShowDTO getCDById(Long id) {
        return productRepository.findCDById(id).map(cdToDtoMapper::toCdShowDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого cd нет!"));
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
}
