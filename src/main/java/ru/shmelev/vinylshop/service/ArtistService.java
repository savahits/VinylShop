package ru.shmelev.vinylshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.shmelev.vinylshop.DTO.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.domain.Artist;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.domain.Product;
import ru.shmelev.vinylshop.mappers.ArtistToDtoMapper;
import ru.shmelev.vinylshop.repository.ArtistRepository;
import ru.shmelev.vinylshop.repository.ProductRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistToDtoMapper artistToDtoMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, ArtistToDtoMapper artistToDtoMapper, ProductRepository productRepository) {
        this.artistRepository = artistRepository;
        this.artistToDtoMapper = artistToDtoMapper;
        this.productRepository = productRepository;
    }

    public List<MultipleArtistsShowDTO> getAllArtists() {
        return artistToDtoMapper.toShowDTOList(artistRepository.findAll());
    }

    public ArtistShowDTO getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(artistToDtoMapper::toShowDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!"));
    }

    public void deleteArtistById(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!");
        }
        artistRepository.deleteById(id);
    }

    public Set<GenreResponseDTO> getGenresByArtistId(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого артиста нет!"));

        return artist.getGenres().stream()
                .map(genre -> new GenreResponseDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toSet());
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
