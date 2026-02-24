package ru.shmelev.vinylshop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.shmelev.vinylshop.DTO.genre.GenreCreateDTO;
import ru.shmelev.vinylshop.DTO.genre.GenreResponseDTO;
import ru.shmelev.vinylshop.DTO.artist.MultipleArtistsShowDTO;
import ru.shmelev.vinylshop.DTO.vinyl.MultipleVinylShowDTO;
import ru.shmelev.vinylshop.service.ArtistService;
import ru.shmelev.vinylshop.service.GenreService;
import ru.shmelev.vinylshop.service.VinylService;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreCreateDTO dto) {
        GenreResponseDTO created = genreService.createGenre(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);
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
