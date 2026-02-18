package ru.shmelev.vinylshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.vinylshop.DTO.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.domain.Genre;
import ru.shmelev.vinylshop.service.ArtistService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<MultipleArtistsShowDTO> getArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("{id}")
    public ArtistShowDTO getArtist(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping("{id}/vinyls")
    public List<MultipleVinylShowDTO> getVinyls(@PathVariable Long id) {
        return artistService.getArtistVinyls(id);
    }

    @GetMapping("/{id}/genres")
    public Set<GenreResponseDTO> getGenresByArtistId(@PathVariable Long id) {
        return artistService.getGenresByArtistId(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtistById(id);
    }

}
