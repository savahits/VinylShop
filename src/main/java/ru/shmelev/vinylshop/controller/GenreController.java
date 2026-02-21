package ru.shmelev.vinylshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.vinylshop.DTO.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
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
        return genreService.getArtistsByGenreId(id);
    }
}
