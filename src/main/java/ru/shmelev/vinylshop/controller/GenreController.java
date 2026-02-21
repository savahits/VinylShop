package ru.shmelev.vinylshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.vinylshop.DTO.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.service.ArtistService;
import ru.shmelev.vinylshop.service.GenreService;
import ru.shmelev.vinylshop.service.VinylService;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreService genreService;
    private final ArtistService artistService;
    private final VinylService vinylService;

    @Autowired
    public GenreController(GenreService genreService, ArtistService artistService, VinylService vinylService) {
        this.genreService = genreService;
        this.artistService = artistService;
        this.vinylService = vinylService;
    }

    @GetMapping
    public List<GenreResponseDTO> getAllGenres() {
        return genreService.findAll();
    }

    @DeleteMapping("{id}")
    public void deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
    }


    @GetMapping("/{id}/artists")
    public List<MultipleArtistsShowDTO> getArtistsByGenre(@PathVariable Long id) {
        return artistService.getArtistsByGenreId(id);
    }

    @GetMapping("/{id}/vinyls")
    public List<MultipleVinylShowDTO> getVinylsByGenre(@PathVariable Long id) {
        return vinylService.getVinylsByGenre(id);
    }
}
