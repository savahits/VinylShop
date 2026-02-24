package ru.shmelev.vinylshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.vinylshop.DTO.artist.ArtistShowDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.artist.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.service.ArtistService;
import ru.shmelev.vinylshop.service.GenreService;
import ru.shmelev.vinylshop.service.VinylService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final GenreService genreService;
    private final VinylService vinylService;

    @Autowired
    public ArtistController(ArtistService artistService, GenreService genreService, VinylService vinylService) {
        this.artistService = artistService;
        this.genreService = genreService;
        this.vinylService = vinylService;
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
        return vinylService.getArtistVinyls(id);
    }

    @GetMapping("/{id}/genres")
    public Set<GenreResponseDTO> getGenresByArtistId(@PathVariable Long id) {
        return genreService.getGenresByArtistId(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtistById(id);
    }

}
